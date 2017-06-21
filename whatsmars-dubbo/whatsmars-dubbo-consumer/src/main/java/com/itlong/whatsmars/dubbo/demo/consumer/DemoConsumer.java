/**
 * Created by shenhongxi on 2017/6/21.
 */
package com.itlong.whatsmars.dubbo.demo.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoConsumer {
	
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application.xml"});
		context.start();
	}

}