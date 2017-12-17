package com.whatsmars.dbrouter.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

/**
 * Created by javahongxi on 2017/12/18.
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        OrderService orderService = context.getBean(OrderService.class);
        AddOrderReq addOrderReq = new AddOrderReq();
        addOrderReq.setUserId("jd123456789");
        addOrderReq.setAmount(new BigDecimal(1000));
        try {
            orderService.addOrder(addOrderReq);
        } catch (Exception e) {
            if (e.getMessage().contains("Table 'demo_ds_0.order_0' doesn't exist")) {
                System.out.println("TEST SUCCESSFUL");
            }
        }
    }
}
