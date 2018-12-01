/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.consumer;

import com.alibaba.dubbo.rpc.RpcContext;
import org.hongxi.whatsmars.dubbo.demo.api.BarService;
import org.hongxi.whatsmars.dubbo.demo.api.DemoService;
import org.hongxi.whatsmars.dubbo.demo.api.vo.Bar;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Future;

public class AsyncConsumer {
	
	public static void main(String[] args) throws Exception {

		//Prevent to get IPV6 address,this way only work in debug mode
		//But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
		System.setProperty("java.net.preferIPv4Stack", "true");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-async-consumer.xml"});
		context.start();

		// 异步调用
		async(context);

	}

	private static void async(ClassPathXmlApplicationContext context) throws Exception {
        // 异步调用
        DemoService demoService5 = (DemoService) context.getBean("demoService5");
        demoService5.sayHello("aysc");
        Future<String> helloFuture = RpcContext.getContext().getFuture();

        BarService barService = (BarService) context.getBean("barService");
        barService.findBar("m123456");
        Future<Bar> barFuture = RpcContext.getContext().getFuture();

        String hello5 = helloFuture.get();
        Bar bar = barFuture.get();
        System.out.println(hello5);
        System.out.println(bar);
    }

}