package org.hongxi.whatsmars.ai.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

/**
 * MCP Server 端口连通性条件。
 * <p>
 * 在 Spring 容器启动时检测 MCP Server 端口是否可达，
 * 只有端口可达时才加载被 {@link ConditionalOnMcpStatusUp} 标注的 Bean。
 * </p>
 *
 * @author hongxi
 */
public class OnMcpStatusUpCondition implements Condition {

    private static final Logger log = LoggerFactory.getLogger(OnMcpStatusUpCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 读取注解属性
        Map<String, Object> attrs = metadata.getAnnotationAttributes(ConditionalOnMcpStatusUp.class.getName());
        if (attrs == null) {
            return false;
        }

        String host = (String) attrs.get("host");
        int port = (int) attrs.get("port");
        int timeout = (int) attrs.get("timeout");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            log.info("MCP Server 端口检测通过: {}:{}，加载 MCP 相关 Bean", host, port);
            return true;
        } catch (Exception e) {
            log.warn("MCP Server 端口检测失败: {}:{}，跳过 MCP 相关 Bean（{}）",
                    host, port, e.getMessage());
            return false;
        }
    }
}
