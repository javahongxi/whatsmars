/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.consumer;

import org.hongxi.whatsmars.dubbo.demo.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoConsumer {
	
	public static void main(String[] args) {

		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
		context.start();

		DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
		String hello = demoService.sayHello("dubbo"); // 执行远程方法
		System.out.println(hello); // 显示调用结果

		DemoService demoService2 = (DemoService) context.getBean("demoService2"); // 获取远程服务代理
		String hello2 = demoService2.sayHello("hessian直连"); // 执行远程方法
		System.out.println(hello2); // 显示调用结果

		DemoService demoService3 = (DemoService) context.getBean("demoService3"); // 获取远程服务代理
		String hello3 = demoService3.sayHello("hessian"); // 执行远程方法
		System.out.println(hello3); // 显示调用结果

	}

}