package org.hongxi.java.nio.selector;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/**
 * @author shenhongxi 2019/8/15
 */
public class ClientController {

    private BlockingQueue<Packet> inner = new LinkedBlockingQueue<>(100);
    private Object lock = new Object();
    private InetSocketAddress remote;
    private Thread thread = new ClientThread(remote);

    public ClientController(String host, int port) {
        remote = new InetSocketAddress(host, port);
    }

    public void start() {
        if (thread.isAlive() || remote == null) {
            return;
        }
        synchronized (lock) {
            thread.start();
        }
    }

    public boolean put(Packet packet) {
        return inner.offer(packet);
    }

    public void clear() {
        inner.clear();
    }

    class ClientThread extends Thread {
        SocketAddress remote;
        SocketChannel channel;

        ClientThread(SocketAddress remote) {
            this.remote = remote;
        }

        @Override
        public void run() {
            try {
                channel = SocketChannel.open();
                channel.configureBlocking(false);
                boolean isSuccess = channel.connect(new InetSocketAddress(30008));
                if (!isSuccess) {
                    while (!channel.finishConnect()) {
                        System.out.println("Client is connecting...");
                    }
                }
                System.out.println("Client is connected.");

                Selector selector = Selector.open();
                channel.register(selector, SelectionKey.OP_WRITE);
                while (selector.isOpen()) {
                    System.out.println("client selector is open");
                    selector.select();
                    System.out.println("client selector select");
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey key = it.next();
                        it.remove();
                        if (!key.isValid()) {
                            continue;
                        }
                        if (key.isWritable()) {
                            write();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (channel != null) {
                    try {
                        channel.close();
                        System.out.println("channel close");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                inner.clear();
            }
        }

        private void write() throws Exception {
            // inner为空时会阻塞
            Packet packet = inner.take();
            synchronized (lock) {
                ByteBuffer body = packet.getBuffer();
                ByteBuffer head = ByteBuffer.allocate(4);
                head.putInt(body.limit());
                // 反转,limit = position;position = 0，之后可 write/get
                head.flip();
                while (head.hasRemaining()) {
                    channel.write(head);
                }

                // 一种校验算法
                Checksum checksum = new Adler32();
                while (body.hasRemaining()) {
                    checksum.update(body.get());
                }

                // 倒带，使 position=0，可以对一个buffer进行"重新"的get或者put.注意rewind方法,不会改变limit值.
                body.rewind();
                while (body.hasRemaining()) {
                    channel.write(body);
                }

                long cks = checksum.getValue();
                ByteBuffer tail = ByteBuffer.allocate(8);
                tail.putLong(cks);
                tail.flip();
                while (tail.hasRemaining()) {
                    channel.write(tail);
                }
            }
        }
    }
}
