package org.hongxi.whatsmars.zk.embed;

import org.apache.curator.test.TestingServer;

import java.io.File;
import java.io.IOException;

/**
 * Embed ZooKeeper.
 * 
 * <p>
 *     Only used for examples
 * </p>
 */
public final class EmbedZookeeperServer {
    
    private static TestingServer testingServer;
    
    /**
     * Embed ZooKeeper.
     * 
     * @param port ZooKeeper port
     */
    public static void start(final int port) {
        try {
            File dir = new File(String.format("%s/test_zk_data/%s/",
                    System.getProperty("user.home"), System.nanoTime()));
            testingServer = new TestingServer(port, dir);
        // CHECKSTYLE:OFF
        } catch (final Exception ex) {
        // CHECKSTYLE:ON
            ex.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                    testingServer.close();
                } catch (final InterruptedException | IOException ignore) {
                }
            }));
        }
    }
}
