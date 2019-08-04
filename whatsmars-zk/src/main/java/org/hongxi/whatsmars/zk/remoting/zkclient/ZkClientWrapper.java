package org.hongxi.whatsmars.zk.remoting.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Zkclient wrapper class that can monitor the state of the connection automatically after the connection is out of time
 * It is also consistent with the use of curator
 *
 * @date 2017/10/29
 */
public class ZkClientWrapper {
    private Logger logger = LoggerFactory.getLogger(ZkClientWrapper.class);
    private long timeout;
    private ZkClient client;
    private volatile KeeperState state;
    private CompletableFuture<ZkClient> completableFuture;
    private volatile boolean started = false;

    public ZkClientWrapper(final String serverAddr, long timeout) {
        this.timeout = timeout;
        completableFuture = CompletableFuture.supplyAsync(() -> new ZkClient(serverAddr, Integer.MAX_VALUE));
    }

    public void start() {
        if (!started) {
            try {
                client = completableFuture.get(timeout, TimeUnit.MILLISECONDS);
//                this.client.subscribeStateChanges(stateListener);
            } catch (Throwable t) {
                logger.error("Timeout! zookeeper server can not be connected in : " + timeout + "ms!", t);
                completableFuture.whenComplete(this::makeClientReady);
            }
            started = true;
        } else {
            logger.warn("Zkclient has already been started!");
        }
    }

    public void addListener(IZkStateListener listener) {
        completableFuture.whenComplete((value, exception) -> {
            this.makeClientReady(value, exception);
            if (exception == null) {
                client.subscribeStateChanges(listener);
            }
        });
    }

    public boolean isConnected() {
//        return client != null && state == KeeperState.SyncConnected;
        return client != null;
    }

    public void createPersistent(String path) {
        check(client);
        client.createPersistent(path, true);
    }

    public void createEphemeral(String path) {
        check(client);
        client.createEphemeral(path);
    }

    public void createPersistent(String path, String data) {
        check(client);
        client.createPersistent(path, data);
    }

    public void createEphemeral(String path, String data) {
        check(client);
        client.createEphemeral(path, data);
    }

    public void delete(String path) {
        check(client);
        client.delete(path);
    }

    public List<String> getChildren(String path) {
        check(client);
        return client.getChildren(path);
    }

    public String getData(String path) {
        check(client);
        return client.readData(path);
    }

    public boolean exists(String path) {
        check(client);
        return client.exists(path);
    }

    public void close() {
        check(client);
        client.close();
    }

    public List<String> subscribeChildChanges(String path, final IZkChildListener listener) {
        check(client);
        return client.subscribeChildChanges(path, listener);
    }

    public void unsubscribeChildChanges(String path, IZkChildListener listener) {
        check(client);
        client.unsubscribeChildChanges(path, listener);
    }

    private void makeClientReady(ZkClient client, Throwable e) {
        if (e != null) {
            logger.error("Got an exception when trying to create zkclient instance, can not connect to zookeeper server, please check!", e);
        } else {
            this.client = client;
//            this.client.subscribeStateChanges(stateListener);
        }
    }

    private void check(ZkClient client) {
        if (client == null) {
            new IllegalStateException("Zookeeper is not connected yet!");
        }
    }


}
