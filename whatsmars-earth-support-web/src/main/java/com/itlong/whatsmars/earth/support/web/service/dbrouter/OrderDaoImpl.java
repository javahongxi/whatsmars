package com.itlong.whatsmars.earth.support.web.service.dbrouter;

import com.itlong.whatsmars.earth.dao.impl.BaseDao;

/**
 * Created by shenhongxi on 16/7/16.
 */
public class OrderDaoImpl extends BaseDao implements OrderDao {

    @Override
    public void insert(OrderDO order) {
        this.sqlSession.insert("Order.insert", order);
    }
}
