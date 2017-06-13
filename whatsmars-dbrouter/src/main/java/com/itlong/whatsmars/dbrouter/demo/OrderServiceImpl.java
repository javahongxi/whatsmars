package com.itlong.whatsmars.dbrouter.demo;

import com.itlong.whatsmars.dbrouter.DbRoute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shenhongxi on 16/7/16.
 */
@Service("orderService")
public class OrderServiceImpl {

    @Autowired
    private OrderDao orderDao;

    @DbRoute(field = "userId", tableStyle = "0000")
    public void addOrder(AddOrderReq req) {
        OrderDO order = new OrderDO();
        order.setUserId(req.getUserId());
        order.setOrderId(order.getUserId() + System.currentTimeMillis());
        order.setAmount(req.getAmount());
        orderDao.insert(order);
    }
}
