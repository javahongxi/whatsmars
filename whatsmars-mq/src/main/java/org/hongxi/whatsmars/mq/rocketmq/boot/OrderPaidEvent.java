package org.hongxi.whatsmars.mq.rocketmq.boot;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPaidEvent implements Serializable {
        private String orderId;
        
        private BigDecimal paidMoney;

        public OrderPaidEvent() {}

        public OrderPaidEvent(String orderId, BigDecimal paidMoney) {
            this.orderId = orderId;
            this.paidMoney = paidMoney;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getPaidMoney() {
            return paidMoney;
        }

        public void setPaidMoney(BigDecimal paidMoney) {
            this.paidMoney = paidMoney;
        }
    }