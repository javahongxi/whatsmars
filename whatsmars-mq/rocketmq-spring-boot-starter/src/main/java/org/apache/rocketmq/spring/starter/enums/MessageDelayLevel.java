package org.apache.rocketmq.spring.starter.enums;

public enum MessageDelayLevel {

    TIME_1S(1),
    TIME_5S(2),
    TIME_10S(3),
    TIME_30S(4),
    TIME_1M(5),
    TIME_2M(6),
    TIME_3M(7),
    TIME_4M(8),
    TIME_5M(9),
    TIME_6M(10),
    TIME_7M(11),
    TIME_8M(12),
    TIME_9M(13),
    TIME_10M(14),
    TIME_20M(15),
    TIME_30M(16),
    TIME_1H(17),
    TIME_2H(18),
    // 新增延迟类型需要在broker配置增加messageDelayLevel
    TIME_6H(19),
    TIME_12H(20);

    public int level;

    MessageDelayLevel(int level) {
        this.level = level;
    }

}