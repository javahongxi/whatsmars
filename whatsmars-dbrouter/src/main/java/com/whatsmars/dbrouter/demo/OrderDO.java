package com.whatsmars.dbrouter.demo;

import java.math.BigDecimal;

/**
 * Created by shenhongxi on 16/7/16.
 */
public class OrderDO extends BaseDO {

    private String orderId;

    private BigDecimal amount;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
