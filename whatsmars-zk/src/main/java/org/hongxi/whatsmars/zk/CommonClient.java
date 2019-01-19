package org.hongxi.whatsmars.zk;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * zookeeper中常用特性，模拟多客户端场景
 */
public class CommonClient implements Watcher {

    // 控制zkClient实例化过程
    private ReentrantLock initLock = new ReentrantLock();
    private Condition initCondition = initLock.newCondition();

    private String servers;
    private int sessionTimeout = 15000; // default


    private ZooKeeper zkClient;

    private boolean canReadOnly;

    public CommonClient(String servers, int sessionTimeout, boolean canReadOnly) throws IOException, InterruptedException {
        this.servers = servers;
        this.sessionTimeout = sessionTimeout;
        this.canReadOnly = canReadOnly;
        initZkClient();
    }

    public String create(String path, byte[] data) throws Exception {
        if (!this.isAlive()) {
            getZkClient(true);
        }
        return this.zkClient.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    private void initZkClient() throws IOException, InterruptedException {
        // 实例化
        initLock.lockInterruptibly();
        try {
            while (!this.isRunning()) {
                // 异步操作,此处，我们让此操作“变成”同步
                zkClient = new ZooKeeper(servers, sessionTimeout, this, canReadOnly);
                initCondition.await(); // 在实例化成功之前，client将不能为用户服务。
            }
        } catch (IOException e) {
            throw e;
        } finally {
            initLock.unlock();
        }
    }

    /**
     * 获得zookeeper实例
     *
     * @param rebuild 是否可以自动重建实例，当发生session过期时或者链接被关闭
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ZooKeeper getZkClient(boolean rebuild) throws IOException, InterruptedException {
        if (this.isRunning()) {
            return zkClient;
        }

        if (!rebuild) {
            if (zkClient != null) {
                zkClient.close();
                zkClient = null;
            }
            return null;
        }
        this.initZkClient();
        return zkClient;

    }

    public boolean isRunning() {
        if (this.zkClient == null) {
            return false;
        }
        States state = this.zkClient.getState();
        switch (state) {
            case NOT_CONNECTED: // first time
            case CLOSED: // error,or expired
            case AUTH_FAILED:
                return false;
            default:
                return true;
        }
    }

    private boolean isAlive() {
        if (zkClient == null) {
            return false;
        }
        States state = zkClient.getState();
        switch (state) {
            case CONNECTED:
            case CONNECTEDREADONLY:
            case CONNECTING:
                return true;
            default:
                return false;
        }
    }

    /**
     * 处理当前zkClient中关于“链接状态迁移”的事件
     */
    public void process(WatchedEvent event) {
        // 如果是“数据变更”事件
        if (event.getType() != EventType.None) {
            //
            processExt(event);
        } else {
            // 如果是链接状态迁移
            // 参见keeperState
            switch (event.getState()) {
                case SyncConnected:
                    // 链接成功
                    initLock.lock();
                    initCondition.notifyAll();
                    initLock.unlock();
                    break;
                case Expired:
                    // 链接成功
                    initLock.lock();
                    if (zkClient != null) {
                        try {
                            zkClient.close();
                        } catch (Exception e) {
                            //
                        }
                    }
                    zkClient = null;
                    initCondition.notifyAll();
                    initLock.unlock();
                    break;
                // session过期
                case Disconnected:
                    // 链接断开，或session迁移
                    System.out.println("Connecting....");
                    break;
                default:
                    break;
            }
        }

    }

    public void processExt(WatchedEvent event) {
        EventType et = event.getType();
        if (et == EventType.None) {
            return;
        }
        switch (et) {
            case NodeChildrenChanged:
            case NodeDataChanged:
            case NodeCreated:
            case NodeDeleted:
                // self
                break;
            default:
                break;
        }
    }

}
