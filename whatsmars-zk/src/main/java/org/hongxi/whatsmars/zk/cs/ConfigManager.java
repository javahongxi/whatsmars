package org.hongxi.whatsmars.zk.cs;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

/**
 * 负责管理所有的“serverType”，对于zk而言，负责创建/删除一级节点。每个一级节点表示一个“serverType”。
 * 每个serverType都有多个子节点，子节点由configServer实例负责注册。
 */
public class ConfigManager {

    Set<String> serverTypes = new CopyOnWriteArraySet<String>();
    private ZooKeeper zkClient;
    private ReentrantLock lock = new ReentrantLock(); // 同步锁，事实上本例可以不用。。仅供参考

    // 当zk环境故障时，是否自动重连，自动重连就意味着开启守护线程检测zk环境，
    // 此方式适用于zk client不关心session过期，“session重建”带来的数据变更（例如临时节点）不会造成系统异常情况下
    private boolean autoReconnected = false;
    private Thread thread = null;
    private Watcher dw = new InnerZK();// default watcher

    private boolean outdate = false;
    // 数据是否过期，在autoReconnected情况下使用，如果没有采用“自动重连”，在session过期后，将不会重建session，
    // 并把outdate标记为true


    public ConfigManager() {
        this(false);
    }

    /**
     * 首次链接必须正常，自动重连，将不会对“首次链接”起作用
     *
     * @param autoReconneted
     */
    public ConfigManager(boolean autoReconneted) {
        this.autoReconnected = autoReconneted;
        if (this.autoReconnected) {
            thread = new Thread(new FailureHandler());
            thread.setDaemon(true);
            thread.start();
        } else {
            try {
                // 回话重建等异常行为
                zkClient = new ZooKeeper(Constants.connectString, 3000, dw, false);
                System.out.println("Reconnected success!...");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public void add(String path) {
        serverTypes.add("/" + path);
        rebuild();
    }

    public void remote(String path) {
        serverTypes.remove(path);
    }

    public Set<String> getServerTypes() {
        return serverTypes;
    }

    public boolean isOutdate() {
        return outdate;
    }


    ////////////////////////////////////////////////inner work//////////////////////////////////


    /**
     * 创建所有serverType的跟节点，比如/cache-server,所有一级节点由此类统一负责创建。
     */
    private void rebuild() {
        lock.lock();
        if (zkClient == null || !zkClient.getState().isConnected()) {
            return;
        }
        for (String path : serverTypes) {
            try {
                Stat stat = zkClient.exists(path, false);
                if (stat == null) {
                    try {
                        zkClient.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    } catch (NodeExistsException ne) {
                        // 如果多个manager同时创建节点，可能会导致此异常，此处忽略它。
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    /**
     * watcher，负责处理事件或者异步操作(本代码实例，未展示异步操作)
     */
    class InnerZK implements Watcher {

        public void process(WatchedEvent event) {
            // 如果是“数据变更”事件，忽略
            if (event.getType() != EventType.None) {
                return;
            }
            // 如果是链接状态迁移
            // 参见keeperState
            switch (event.getState()) {
                case SyncConnected:
                    System.out.println("Connected...");
                    rebuild(); // 每次重连，都检测一下数据状态。
                    outdate = false;
                    break;
                case Expired:
                    System.out.println("Expired...");
                    // session重建
                    outdate = true;
                    break;
                // session过期
                case Disconnected:
                    // 链接断开，或session迁移
                    System.out.println("Connecting....");
                    break;
                case AuthFailed:
                    if (autoReconnected && thread.isAlive()) {
                        thread.interrupt();
                    }
                    throw new RuntimeException("ZK Connection auth failed...");
                default:
                    break;
            }

        }

    }

    class FailureHandler implements Runnable {
        /**
         * zk故障担保线程，如果需要故障检测或者容错，请将此实例交付给单独线程执行
         * 比如：因为网络问题，zk实例将可能长时间处于无法链接状态，或者其它异常，导致zk实例化出错等
         */
        public void run() {
            try {
                int i = 0;
                int l = 100; // 每次重建，将时间延迟100ms
                while (true) {
                    System.out.println("Manager handler,running...tid: " + Thread.currentThread().getId());
                    if (zkClient == null || (zkClient.getState() == States.NOT_CONNECTED || zkClient.getState() == States.CLOSED)) {
                        lock.lock();
                        try {
                            // 回话重建等异常行为
                            zkClient = new ZooKeeper(Constants.connectString, 3000, dw, false);
                            System.out.println("Reconnected success!...");
                        } catch (Exception e) {
                            e.printStackTrace();
                            i++;
                            Thread.sleep(3000 + i * l);// 在zk环境异常情况下，每3秒重试一次
                        } finally {
                            lock.unlock();
                        }
                        continue;
                    }

                    if (zkClient.getState().isConnected()) {
                        Thread.sleep(3000); // 如果被“中断”，直接退出
                        i = 0;
                    }

                }
            } catch (InterruptedException e) {
                System.out.println("Exit...");
                if (zkClient != null) {
                    try {
                        zkClient.close();
                    } catch (Exception ze) {
                        ze.printStackTrace();
                    }
                }
            }
        }
    }

}
