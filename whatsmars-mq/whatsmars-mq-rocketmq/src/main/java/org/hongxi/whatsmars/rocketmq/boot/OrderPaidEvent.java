package org.hongxi.whatsmars.rocketmq.boot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderPaidEvent implements Serializable {

    private String orderId;
    private BigDecimal paidMoney;
}