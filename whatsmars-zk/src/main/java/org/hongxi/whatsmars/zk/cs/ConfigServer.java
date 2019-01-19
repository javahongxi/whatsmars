package org.hongxi.whatsmars.zk.cs;

import java.util.Random;
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
 * configServer，负责向zk注册当前server的信息，可被configClient获得信息。
 */
public class ConfigServer {

    private ZooKeeper zkClient;
    private String path;
    private String serverType; // 当前configServer的类型，我们假设一个configServer实例持有一种“serverType”
    private ReentrantLock lock = new ReentrantLock();
    private boolean autoReconnected = false;
    private Thread thread = null;
    private Watcher dw = new InnerZK(); // default watcher

    private boolean outdate = false;
    // 数据是否过期，在autoReconnected情况下使用，如果没有采用“自动重连”，在session过期后，将不会重建session，并把outdate标记为true

    // 控制首次访问
    private Object tag = new Object();
    private boolean init = false;

    public ConfigServer(String serverType) {
        this(serverType, false);
    }

    public ConfigServer(String serverType, boolean autoReconnected) {
        this.serverType = serverType;
        this.autoReconnected = autoReconnected;
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

    public boolean isOutdate() {
        return outdate;
    }

    /**
     * 注册server信息，从zk角度来说，就是创建serverType的一个子节点。
     *
     * @return
     */
    private boolean register() {
        lock.lock();
        init = false;
        try {
            Stat stat = zkClient.exists("/" + serverType, true); // 注册“父节点”watch，跟踪父节点的创建/删除
            // 创建跟节点：/cache-server
            // 如果跟节点不存在，则等待configManager去创建，创建成功后，将会在下文的watch事件中创建此子节点。
            if (stat == null) {
                return false;
            }
            // 创建临时子节点：/cache-server/cs01;
            Random r = new Random();
            String data = "127.0.0.1:" + r.nextInt(65535); // tmp data,模拟一个ip +
            // port参数
            path = zkClient.create("/" + serverType + "/id_", data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("Register path:" + path);
            init = true;
            synchronized (tag) {
                tag.notifyAll();
            }
        } catch (NodeExistsException ne) {
            // ignore.
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }

    public String getPath() {
        synchronized (tag) {
            while (!init) {
                try {
                    tag.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return path;
    }

    class InnerZK implements Watcher {

        public void process(WatchedEvent event) {
            // 如果是“数据变更”事件
            if (event.getType() != EventType.None) {
                switch (event.getType()) {
                    // 如果其父节点(/serverType)被创建，
                    // 此时configServer也开始注册其子节点信息，watcher在下文中SyncConnected中注册。
                    case NodeCreated:
                        register();
                        break;
                    case NodeDeleted:
                        // 如果父节点被删除，那么此后子节点也将不复存在
                        path = null;
                        register();// 注册watch,检测父节点/serverType再次创建。
                        break;
                    default:
                        break;
                }
                return;
            }
            // 如果是链接状态迁移
            // 参见keeperState
            switch (event.getState()) {
                case SyncConnected:
                    System.out.println("Connected...");
                    // 如果path == null,则表明是首次链接或者session重建。
                    if (path == null) {
                        try {
                            register(); // 创建子节点，并对其父节点注册watch。
                            outdate = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Expired:
                    System.out.println("Expired...");
                    outdate = true;
                    init = true;
                    synchronized (tag) {
                        tag.notifyAll();
                    }
                    break;
                // session过期
                case Disconnected:
                    // 链接断开，或session迁移
                    System.out.println("Connecting....");
                    break;
                case AuthFailed:
                    init = true;
                    synchronized (tag) {
                        tag.notifyAll();
                    }
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

        public void run() {
            try {
                int i = 0;
                int l = 10;
                while (true) {
                    System.out.println("Server handler,running...tid: " + Thread.currentThread().getId());
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
