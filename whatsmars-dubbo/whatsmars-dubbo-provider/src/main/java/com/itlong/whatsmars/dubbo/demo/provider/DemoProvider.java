/**
 * Created by shenhongxi on 2017/6/21.
 */
package com.itlong.whatsmars.dubbo.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class DemoProvider {

	public static void main(String[] args) throws IOException {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"application.xml"});
		context.start();

		System.out.println("service started");
		System.in.read();

	}

}