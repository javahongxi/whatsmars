package com.whatsmars.base.nio.qing;

import java.nio.channels.SocketChannel;

public interface Handler {

	public void handle(SocketChannel channel, String from);
}
