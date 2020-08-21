package org.hongxi.whatsmars.netty.nio;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by jjenkov on 24-10-2015.
 */
public class Server {

    private SocketAcceptor socketAcceptor = null;
    private SocketProcessor socketProcessor = null;

    private int tcpPort = 0;
    private MessageReaderFactory messageReaderFactory = null;
    private MessageProcessor messageProcessor = null;

    public Server(int tcpPort, MessageReaderFactory messageReaderFactory, MessageProcessor messageProcessor) {
        this.tcpPort = tcpPort;
        this.messageReaderFactory = messageReaderFactory;
        this.messageProcessor = messageProcessor;
    }

    public void start() throws IOException {

        Queue socketQueue = new ArrayBlockingQueue(1024); //move 1024 to ServerConfig

        this.socketAcceptor = new SocketAcceptor(tcpPort, socketQueue);


        MessageBuffer readBuffer  = new MessageBuffer();
        MessageBuffer writeBuffer = new MessageBuffer();

        this.socketProcessor = new SocketProcessor(socketQueue, readBuffer, writeBuffer,  this.messageReaderFactory, this.messageProcessor);

        Thread acceptorThread  = new Thread(this.socketAcceptor);
        Thread processorThread = new Thread(this.socketProcessor);

        acceptorThread.start();
        processorThread.start();
    }


}
