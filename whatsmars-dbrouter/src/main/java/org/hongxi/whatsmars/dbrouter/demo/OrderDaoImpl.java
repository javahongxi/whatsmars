package org.hongxi.whatsmars.dbrouter.demo;

import org.springframework.stereotype.Repository;

/**
 * Created by shenhongxi on 16/7/16.
 */
@Repository("orderDao")
public class OrderDaoImpl extends BaseDao implements OrderDao {

    @Override
    public void insert(OrderDO order) {
        this.sqlSession.insert("Order.insert", order);
    }
}
