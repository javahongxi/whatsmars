
package org.hongxi.whatsmars.dubbo.autoconfigure;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static org.hongxi.whatsmars.dubbo.autoconfigure.util.DubboUtils.*;


/**
 * Dubbo Config {@link ConfigurationProperties Properties} with prefix "dubbo.config"
 *
 * @see ConfigurationProperties
 */
@ConfigurationProperties(prefix = DUBBO_CONFIG_PREFIX)
public class DubboConfigProperties {

    /**
     * Indicates multiple properties binding from externalized configuration or not.
     */
    private boolean multiple = DEFAULT_MULTIPLE_CONFIG_PROPERTY_VALUE;

    /**
     * Indicates override {@link ConfigUtils#getProperties() Dubbo config properties} from externalized configuration
     * or not.
     */
    private boolean override = DEFAULT_OVERRIDE_CONFIG_PROPERTY_VALUE;

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }
}
