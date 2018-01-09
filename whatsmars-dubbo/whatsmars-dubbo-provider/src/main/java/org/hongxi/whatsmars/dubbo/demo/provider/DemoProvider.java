/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.provider;

import java.io.IOException;

public class DemoProvider {

	public static void main(String[] args) throws IOException {

		//Prevent to get IPV6 address,this way only work in debug mode
		//But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
		System.setProperty("java.net.preferIPv4Stack", "true");
		com.alibaba.dubbo.container.Main.main(args);

	}

}