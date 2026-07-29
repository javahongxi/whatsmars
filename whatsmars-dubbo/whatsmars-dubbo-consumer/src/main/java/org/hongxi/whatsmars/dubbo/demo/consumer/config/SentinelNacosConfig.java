package org.hongxi.whatsmars.dubbo.demo.consumer.config;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sentinel Nacos 数据源配置
 * 从 Nacos 动态加载 Sentinel 流控规则和降级规则
 *
 * <p>
 *     示例配置可用单元测试类 SentinelRulesTest 生成，也可直接从 README 拷贝
 * </p>
 *
 * @author hongxi
 */
@Configuration(proxyBeanMethods = false)
public class SentinelNacosConfig {

    private static final Logger log = LoggerFactory.getLogger(SentinelNacosConfig.class);

    @Value("${sentinel.nacos.server-addr:127.0.0.1:8848}")
    private String serverAddr;

    @Value("${sentinel.nacos.namespace:}")
    private String namespace;

    @Value("${sentinel.nacos.username:nacos}")
    private String username;

    @Value("${sentinel.nacos.password:nacos}")
    private String password;

    @Value("${sentinel.nacos.flow.group-id:SENTINEL_GROUP}")
    private String flowGroupId;

    @Value("${sentinel.nacos.flow.data-id:dubbo.demo.consumer.flow.rules}")
    private String flowDataId;

    @Value("${sentinel.nacos.degrade.group-id:SENTINEL_GROUP}")
    private String degradeGroupId;

    @Value("${sentinel.nacos.degrade.data-id:dubbo.demo.consumer.degrade.rules}")
    private String degradeDataId;

    /**
     * 初始化 Sentinel Nacos 数据源
     */
    @PostConstruct
    public void init() {
        log.info("Initializing Sentinel Nacos DataSource, serverAddr: {}, flowDataId: {}, degradeDataId: {}", 
                serverAddr, flowDataId, degradeDataId);
        
        // 加载流控规则
        loadFlowRules();
        
        // 加载降级规则
        loadDegradeRules();
        
        log.info("Sentinel Nacos DataSource initialized successfully");
    }

    /**
     * 从 Nacos 加载流控规则
     */
    private void loadFlowRules() {
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(
                buildNacosProperties(), 
                flowGroupId, 
                flowDataId,
                source -> JSON.parseObject(source, new TypeReference<>() {})
        );
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        log.info("Flow rules datasource registered from Nacos: groupId={}, dataId={}", flowGroupId, flowDataId);
    }

    /**
     * 从 Nacos 加载降级规则
     */
    private void loadDegradeRules() {
        ReadableDataSource<String, List<DegradeRule>> degradeRuleDataSource = new NacosDataSource<>(
                buildNacosProperties(), 
                degradeGroupId, 
                degradeDataId,
                source -> JSON.parseObject(source, new TypeReference<>() {})
        );
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        log.info("Degrade rules datasource registered from Nacos: groupId={}, dataId={}", degradeGroupId, degradeDataId);
    }

    /**
     * 构建 Nacos 配置属性
     */
    private Properties buildNacosProperties() {
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("username", username);
        properties.put("password", password);
        
        if (namespace != null && !namespace.isEmpty()) {
            properties.put("namespace", namespace);
        }
        
        return properties;
    }
}
