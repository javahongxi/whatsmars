/**
 * Created by shenhongxi on 2017/6/21.
 */
package org.hongxi.whatsmars.dubbo.demo.consumer;

import org.apache.dubbo.rpc.RpcContext;
import org.hongxi.whatsmars.dubbo.demo.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class AsyncConsumer2 {
	
	public static void main(String[] args) throws Exception {

		//Prevent to get IPV6 address,this way only work in debug mode
		//But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
		System.setProperty("java.net.preferIPv4Stack", "true");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/dubbo-demo-consumer.xml"});
		context.start();

        final DemoService demoService = (DemoService) context.getBean("demoService");

        Future<String> f = RpcContext.getContext().asyncCall(new Callable<String>() {
            public String call() throws Exception {
                return demoService.sayHello("async call request");
            }
        });

        System.out.println("async call ret :" + f.get());

        RpcContext.getContext().asyncCall(new Runnable() {
            public void run() {
                demoService.sayHello("oneway call request1");
                demoService.sayHello("oneway call request2");
            }
        });

        System.in.read();

	}

}