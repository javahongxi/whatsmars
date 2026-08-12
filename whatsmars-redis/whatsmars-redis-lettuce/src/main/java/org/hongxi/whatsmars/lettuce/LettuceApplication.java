package org.hongxi.whatsmars.lettuce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 运行前先启动两个 Redis 集群（cache:7001-7006, session:7011-7016）
 * <p>
 *     sh redis-cluster.sh start
 * </p>
 */
@SpringBootApplication
public class LettuceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LettuceApplication.class, args);
    }
}
