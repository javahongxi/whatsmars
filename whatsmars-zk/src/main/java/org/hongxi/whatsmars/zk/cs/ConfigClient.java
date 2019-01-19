package org.hongxi.whatsmars.zk.cs;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * configServer注册的数据，configClient消费。设计思路和configServer一致。
 * 针对client获取数据的方式很多，如下是2中思路 1) 使用zk watch，当数据变更时即使获取 2) 开启守护线程，间歇性读取
 * 这两中方式各有优缺点，使用watch，间接的增加了zk环境的事件push的压力和“波及面”，当客户端为N，每个客户端wath的节点数为M
 * 那么在极端情况下，zk需要分发的watch个数为M*N，而且可能因为configServer的数据变更较多，导致watch处罚次数较多。
 * 同时因为网络问题，client可能丢失某些事件而导致无法及时获取数据。
 * <p>
 * 如果使用2)，直接避免了1)所带来的问题，但是因为间歇性的读取，可能导致zk数据变更无法被即使获得。同时还有其他的问题，比如如果当前
 * client所关注的serverType集合较大，而且数据尺寸较大，可能会导致每次全量读取都会消耗较长的时间和网络IO，如果“间歇时间”较短 +
 * 数据较大， 也会对整个环境有很大影响。
 * <p>
 * 不过，此实例假设configServer所注册的数据较小，configClient与zk之间的网络情况较佳。因此我决定采取2)
 */
public class ConfigClient {

    private ZooKeeper zkClient;
    // inner cache；key:serverType,value:serverList
    private Map<String, Set<String>> servers = new ConcurrentHashMap<String, Set<String>>();
    // 当前configClient需要获取的数据分类。即当前client对何种serverType感兴趣
    private Set<String> serverTypes = new HashSet<String>();
    private Watcher dw = new InnerZK(); // 只关注链接状态迁移事件，区别于configServer

    private ReentrantLock lock = new ReentrantLock();

    // 对于首次链接，或者网络异常进行一次阻塞方式的数据同步，将阻塞其他线程对client的操作。
    private Object tag = new Object();
    private boolean init = false; // 是否已经初始化

    private Thread thread = new DumpThread(); // 数据同步线程

    public ConfigClient(String... types) {
        if (types == null || types.length == 0) {
            throw new RuntimeException("ConfigClient,serverTypes cant be empty..please check!");
        }
        for (String type : types) {
            if (type == null || type.isEmpty() || type.contains("/")) {
                System.out.println("ConfigClient,ignore :" + type);
                continue;
            }
            serverTypes.add(type);
        }
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * 获得指定serverType的节点数据
     *
     * @param serverType
     * @return
     */
    public Set<String> getServers(String serverType) {
        synchronized (tag) {
            while (!init) {
                try {
                    // 阻塞直到成功，在链接异常时的dump期间，所有客户端访问需要阻塞；在dumpThread中dump，不会阻塞。
                    // 当然你可以设计为不阻塞。
                    // 不过需要注意“首次实例化一定要阻塞”，因为configClient实例化zk是在dumpThread中，
                    // 如果执行new ConfigClient()之后，立即调用getServers方法，可能导致一个调用者获得空集合；
                    tag.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        if (servers.containsKey(serverType)) {
            return Collections.unmodifiableSet(servers.get(serverType));
        }
        return null;
    }

    public Set<String> getServerTypes() {
        return serverTypes;
    }

    // ///////////////////////////////////////////inner
    // work////////////////////////////

    /**
     * 和zk同步数据
     */
    private void dump() {
        lock.lock();
        try {
            for (String serverType : serverTypes) {
                dump(serverType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 同步制定serverType的数据
     *
     * @param serverType
     */
    private void dump(String serverType) {
        lock.lock();
        try {
            String parent = "/" + serverType;
            List<String> children = zkClient.getChildren(parent, false, null); // 注册watch
            if (children == null || children.isEmpty()) {
                return;
            }
            Set<String> snap = new HashSet<String>();
            ;
            for (String path : children) {
                byte[] data = zkClient.getData(parent + "/" + path, false, null);
                snap.add(new String(data));
            }
            servers.put(serverType, snap);// 直接替换
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    class InnerZK implements Watcher {

        public void process(WatchedEvent event) {
            // 如果是“数据变更”事件,不关注数据变更事件，事实上，我们也不会注册此类型事件
            if (event.getType() != EventType.None) {
                return;
            }
            // 如果是链接状态迁移
            // 参见keeperState
            switch (event.getState()) {
                case SyncConnected:
                    System.out.println("Connected...");
                    init = false;
                    dump(); // 每次链接重建，都需要手动dump一下数据
                    init = true;
                    synchronized (tag) {
                        tag.notifyAll();
                    }
                    break;
                case Expired:
                    System.out.println("Expired...");
                    // 将在DumpThread中自动创建
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
                    if (thread.isAlive()) {
                        thread.interrupt();
                        servers.clear();
                    }
                default:
                    break;
            }

        }

    }

    class DumpThread extends Thread {

        @Override
        public void run() {
            try {
                Random r = new Random();
                int i = 0;
                while (true) {
                    System.out.println("Client handler,running...tid: " + Thread.currentThread().getId());
                    // 如果zk尚未实例化，或者链接异常
                    if (zkClient == null || (zkClient.getState() == States.NOT_CONNECTED || zkClient.getState() == States.CLOSED)) {
                        lock.lock();
                        try {
                            // 回话重建等异常行为
                            zkClient = new ZooKeeper(Constants.connectString, 10000, dw, true);
                            System.out.println("Reconnected success!...");
                        } catch (Exception e) {
                            e.printStackTrace();
                            i++;
                            // 惰性延迟，每失败一次，多休眠100ms
                            Thread.sleep(2000 + i * 100);
                        } finally {
                            lock.unlock();
                        }
                        continue;
                    }

                    if (zkClient.getState().isConnected()) {
                        // 休眠，为了避免client网络“大规模”故障时，同时访问zk带来的性能波动
                        Thread.sleep(1000 + r.nextInt(6000));
                        dump();
                        i = 0;// reset
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
