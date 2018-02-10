
package org.hongxi.whatsmars.dubbo.actuate.endpoint;

import com.alibaba.dubbo.common.Version;
import org.hongxi.whatsmars.dubbo.autoconfigure.util.DubboUtils;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hongxi.whatsmars.dubbo.autoconfigure.util.DubboUtils.*;

/**
 * Actuator {@link Endpoint} to expose Dubbo Meta Data
 *
 * @see Endpoint
 */
@ConfigurationProperties(prefix = "endpoints.dubbo", ignoreUnknownFields = false)
public class DubboEndpoint extends AbstractEndpoint<Map<String, Object>> {

    public static final String DUBBO_SHUTDOWN_ENDPOINT_URI = "/shutdown";

    public static final String DUBBO_CONFIGS_ENDPOINT_URI = "/configs";

    public static final String DUBBO_SERVICES_ENDPOINT_URI = "/services";

    public static final String DUBBO_REFERENCES_ENDPOINT_URI = "/references";

    public static final String DUBBO_PROPERTIES_ENDPOINT_URI = "/properties";

    public DubboEndpoint() {
        super("dubbo", true, false);
    }

    @Override
    public Map<String, Object> invoke() {

        Map<String, Object> metaData = new LinkedHashMap<>();

        metaData.put("timestamp", System.currentTimeMillis());

        Map<String, String> versions = new LinkedHashMap<>();
        versions.put("dubbo-spring-boot", Version.getVersion(DubboUtils.class, "1.0.0"));
        versions.put("dubbo", Version.getVersion());

        Map<String, String> urls = new LinkedHashMap<>();
        urls.put("dubbo", DUBBO_GITHUB_URL);
        urls.put("google-group", DUBBO_GOOGLE_GROUP_URL);
        urls.put("github", DUBBO_SPRING_BOOT_GITHUB_URL);
        urls.put("issues", DUBBO_SPRING_BOOT_ISSUES_URL);
        urls.put("git", DUBBO_SPRING_BOOT_GIT_URL);

        Map<String, String> endpoints = new LinkedHashMap<>();
        endpoints.put("shutdown", DUBBO_SHUTDOWN_ENDPOINT_URI);
        endpoints.put("configs", DUBBO_CONFIGS_ENDPOINT_URI);
        endpoints.put("services", DUBBO_SERVICES_ENDPOINT_URI);
        endpoints.put("references", DUBBO_REFERENCES_ENDPOINT_URI);
        endpoints.put("properties", DUBBO_PROPERTIES_ENDPOINT_URI);

        metaData.put("versions", versions);
        metaData.put("urls", urls);
        metaData.put("endpoints", endpoints);

        return metaData;
    }
}
