package com.whatsmars.base.nio.qing;


public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		int port = 30008;
		ServerController sc = new ServerController(port);
		sc.start();
		Thread.sleep(2000);
		ClientController cc = new ClientController("127.0.0.1", port);
		cc.start();
		Packet p1 = Packet.wrap("Hello,I am first!");
		cc.put(p1);
		Packet p2 = Packet.wrap("Hello,I am second!");
		cc.put(p2);
		Packet p3 = Packet.wrap("Hello,I am third!");
		cc.put(p3);

	}

}
