package org.hongxi.java.nio.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * @author shenhongxi 2019/8/15
 */
public class ServerController {
    private int port;
    private Thread thread = new ServerThread();
    private Object lock = new Object();

    public ServerController() {
        this(0);
    }

    public ServerController(int port) {
        this.port = port;
    }

    public void start() {
        if (thread.isAlive()) {
            return;
        }
        synchronized (lock) {
            thread.start();
            System.out.println("Server starting....");
        }
    }


    class ServerThread extends Thread {
        private static final int TIMEOUT = 3000;

        @Override
        public void run() {
            try {
                ServerSocketChannel channel = null;
                try {
                    channel = ServerSocketChannel.open();
                    channel.configureBlocking(false);
                    channel.socket().setReuseAddress(true);
                    channel.socket().bind(new InetSocketAddress(port));
                    Selector selector = Selector.open();
                    channel.register(selector, SelectionKey.OP_ACCEPT);
                    while (selector.isOpen()) {
                        System.out.println("Server is running, port:" + channel.socket().getLocalPort());
                        if (selector.select(TIMEOUT) == 0) {
                            System.out.println("select timeout");
                            continue;
                        }
                        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                        while (it.hasNext()) {
                            SelectionKey key = it.next();
                            it.remove();
                            if (!key.isValid()) {
                                continue;
                            }
                            if (key.isAcceptable()) {
                                accept(key);
                            } else if (key.isReadable()) {
                                read(key);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void accept(SelectionKey key) throws Exception {
            SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
            System.out.println("accept channel: " + socketChannel.getRemoteAddress());
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);
        }

        private void read(SelectionKey key) {
            SocketChannel channel = (SocketChannel) key.channel();
            handle(channel);
        }

        private void handle(SocketChannel channel) {
            try {
                boolean eof = false;
                System.out.println("channel is open:" + channel.isOpen());
                while (channel.isOpen()) {
                    // int for data-size
                    ByteBuffer head = ByteBuffer.allocate(4);
                    while (true) {
                        int cb = channel.read(head);
                        if (cb == -1) {
                            throw new RuntimeException("EOF error,data lost!");
                        }
                        if (isFull(head)) {
                            break;
                        }
                    }
                    head.flip();
                    int dataSize = head.getInt();
                    if (dataSize <= 0) {
                        throw new RuntimeException("Data format error,something lost???");
                    }
                    ByteBuffer body = ByteBuffer.allocate(dataSize);
                    while (true) {
                        int cb = channel.read(body);
                        if (cb == -1) {
                            throw new RuntimeException("EOF error,data lost!");
                        } else if (cb == 0 && this.isFull(body)) {
                            break;
                        }
                    }
                    // int for data-size
                    ByteBuffer tail = ByteBuffer.allocate(8);
                    while (true) {
                        int cb = channel.read(tail);
                        if (cb == -1) {
                            eof = true;
                        }
                        if (isFull(tail)) {
                            break;
                        }
                    }
                    tail.flip();
                    long sck = tail.getLong();
                    Checksum checksum = new Adler32();
                    checksum.update(body.array(), 0, dataSize);
                    long cck = checksum.getValue();
                    if (sck != cck) {
                        throw new RuntimeException("Sorry,some data lost or be modified,please check!");
                    }
                    body.flip();
                    Packet packet = Packet.wrap(body);
                    System.out.println(packet.getDataAsString());
                    if (eof) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }

        private boolean isFull(ByteBuffer byteBuffer) {
            return byteBuffer.position() == byteBuffer.capacity() ? true : false;
        }
    }
}
