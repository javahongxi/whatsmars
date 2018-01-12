/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.consumer;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.service.EchoService;
import org.hongxi.whatsmars.dubbo.demo.api.BarService;
import org.hongxi.whatsmars.dubbo.demo.api.DemoService;
import org.hongxi.whatsmars.dubbo.demo.api.vo.Bar;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Future;

public class DemoConsumer {
	
	public static void main(String[] args) throws Exception {

		//Prevent to get IPV6 address,this way only work in debug mode
		//But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
		System.setProperty("java.net.preferIPv4Stack", "true");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
		context.start();

		// dubbo protocol
		DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
		String hello = demoService.sayHello("dubbo"); // 执行远程方法
		System.out.println(hello); // 显示调用结果

		// hessian protocol 直连
		DemoService demoService2 = (DemoService) context.getBean("demoService2");
		String hello2 = demoService2.sayHello("hessian直连");
		System.out.println(hello2);

		// hessian protocol
		DemoService demoService3 = (DemoService) context.getBean("demoService3");
		String hello3 = demoService3.sayHello("hessian");
		System.out.println(hello3);

		// service group
		DemoService demoService4 = (DemoService) context.getBean("demoService4");
		String hello4 = demoService4.sayHello("group:new");
		System.out.println(hello4);

		// 回声测试可用性
        EchoService echoService = (EchoService) demoService;
        Object status = echoService.$echo("OK");
        System.out.println("回声测试：" + status.equals("OK"));

        System.out.println("#######################ALL SUCCESSFUL##########################");

	}

}