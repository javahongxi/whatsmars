/**
 * Created by shenhongxi on 2017/6/21.
 */
package com.whatsmars.dubbo.demo.consumer;

import com.whatsmars.dubbo.demo.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoConsumer {
	
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring/dubbo-demo-consumer.xml"});
		context.start();

		DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
		String hello = demoService.sayHello("world"); // 执行远程方法

		System.out.println(hello); // 显示调用结果

	}

}