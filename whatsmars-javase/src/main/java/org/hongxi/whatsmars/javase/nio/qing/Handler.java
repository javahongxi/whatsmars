package org.hongxi.whatsmars.javase.nio.qing;

import java.nio.channels.SocketChannel;

public interface Handler {

	public void handle(SocketChannel channel, String from);
}
