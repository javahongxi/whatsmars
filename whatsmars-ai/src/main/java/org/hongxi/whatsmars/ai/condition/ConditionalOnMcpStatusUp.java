package org.hongxi.whatsmars.ai.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 自定义条件注解：当 MCP Server（端口 8886）启动后才加载相关 Bean。
 * <p>
 * 通过检测 TCP 端口连通性判断 MCP Server 是否就绪，
 * 若端口不可达则跳过被标注的 {@link org.springframework.context.annotation.Configuration} 或 {@link org.springframework.stereotype.Component}。
 * </p>
 *
 * @author hongxi
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnMcpStatusUpCondition.class)
public @interface ConditionalOnMcpStatusUp {

    /**
     * MCP Server 主机，默认 localhost
     */
    String host() default "localhost";

    /**
     * MCP Server 端口，默认 8886
     */
    int port() default 8886;

    /**
     * 连接超时时间（毫秒），默认 2000ms
     */
    int timeout() default 2000;
}
