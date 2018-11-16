/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hongxi.whatsmars.dubbo.sentinel.demo2;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.SentinelRpcException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.dubbo.rpc.RpcResult;
import org.hongxi.whatsmars.dubbo.sentinel.consumer.ConsumerConfiguration;
import org.hongxi.whatsmars.dubbo.sentinel.consumer.FooServiceConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Please add the following VM arguments:
 * <pre>
 * -Djava.net.preferIPv4Stack=true
 * -Dcsp.sentinel.api.port=8721
 * -Dproject.name=dubbo-consumer-demo
 * </pre>
 *
 * @author Eric Zhao
 */
public class FooConsumerBootstrap {

    private static final String RES_KEY = "org.hongxi.whatsmars.dubbo.sentinel.FooService:sayHello(java.lang.String)";
    private static final String INTERFACE_RES_KEY = "org.hongxi.whatsmars.dubbo.sentinel.FooService";

    private static final ExecutorService pool = Executors.newFixedThreadPool(10,
        new NamedThreadFactory("dubbo-consumer-pool"));

    public static void main(String[] args) {
        initFlowRule();
        registerFallback();

        AnnotationConfigApplicationContext consumerContext = new AnnotationConfigApplicationContext();
        consumerContext.register(ConsumerConfiguration.class);
        consumerContext.refresh();

        FooServiceConsumer service = consumerContext.getBean(FooServiceConsumer.class);
        for (int i = 0; i < 10; i++) {
            pool.submit(() -> {
                try {
                    String message = service.sayHello("Eric");
                    System.out.println("Success: " + message);
                } catch (Exception ex) {
                    if (BlockException.isBlockException(ex)) {
                        System.out.println("service.sayHello degrade");
                    } else {
                        ex.printStackTrace();
                    }
                }
            });
            pool.submit(() -> System.out.println("Another: " + service.doAnother()));
        }
    }

    private static void initFlowRule() {
        DegradeRule rule = new DegradeRule();
        rule.setResource(RES_KEY);
        rule.setCount(1);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setTimeWindow(1);
        rule.setLimitApp("default");
        DegradeRuleManager.loadRules(Collections.singletonList(rule));
    }

    private static void registerFallback() {
        // Register fallback handler for consumer.
        // If you only want to handle degrading, you need to
        // check the type of BlockException.
        DubboFallbackRegistry.setConsumerFallback((a, b, ex) ->
            new RpcResult("Error: " + ex.getClass().getTypeName()));
    }
}
