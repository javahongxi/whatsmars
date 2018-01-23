
package org.hongxi.whatsmars.dubbo.starter.autoconfigure;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.hongxi.whatsmars.dubbo.starter.util.DubboUtils.DUBBO_SCAN_PREFIX;


/**
 * Dubbo Scan {@link ConfigurationProperties Properties} with prefix "dubbo.scan"
 *
 * @see ConfigurationProperties
 */
@ConfigurationProperties(prefix = DUBBO_SCAN_PREFIX)
public class DubboScanProperties {

    /**
     * The basePackages to scan , the multiple-value is delimited by comma
     *
     * @see EnableDubbo#scanBasePackages()
     */
    private Set<String> basePackages = new LinkedHashSet<>();

    public Set<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(Set<String> basePackages) {
        this.basePackages = basePackages;
    }

}
