
package org.hongxi.whatsmars.dubbo.autoconfigure.util;

import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class DubboUtils {

    /**
     * line separator
     */
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");


    /**
     * The separator of property name
     */
    public static final String PROPERTY_NAME_SEPARATOR = ".";

    /**
     * The prefix of property name of Dubbo
     */
    public static final String DUBBO_PREFIX = "dubbo";

    /**
     * The prefix of property name for Dubbo scan
     */
    public static final String DUBBO_SCAN_PREFIX = DUBBO_PREFIX + PROPERTY_NAME_SEPARATOR + "scan";

    /**
     * The prefix of property name for Dubbo Config.ØØ
     */
    public static final String DUBBO_CONFIG_PREFIX = DUBBO_PREFIX + PROPERTY_NAME_SEPARATOR + "config";

    /**
     * The property name of base packages to scan
     * <p>
     * The default value is empty set.
     */
    public static final String BASE_PACKAGES_PROPERTY_NAME = DUBBO_SCAN_PREFIX + PROPERTY_NAME_SEPARATOR + "basePackages";

    /**
     * The property name of multiple properties binding from externalized configuration
     * <p>
     * The default value is {@link #DEFAULT_MULTIPLE_CONFIG_PROPERTY_VALUE}
     */
    public static final String MULTIPLE_CONFIG_PROPERTY_NAME = DUBBO_CONFIG_PREFIX + PROPERTY_NAME_SEPARATOR + "multiple";

    /**
     * The default value of multiple properties binding from externalized configuration
     */
    public static final boolean DEFAULT_MULTIPLE_CONFIG_PROPERTY_VALUE = false;

    /**
     * The property name of override Dubbo config
     * <p>
     * The default value is {@link #DEFAULT_OVERRIDE_CONFIG_PROPERTY_VALUE}
     */
    public static final String OVERRIDE_CONFIG_PROPERTY_NAME = DUBBO_CONFIG_PREFIX + PROPERTY_NAME_SEPARATOR + "override";

    /**
     * The default property value of  override Dubbo config
     */
    public static final boolean DEFAULT_OVERRIDE_CONFIG_PROPERTY_VALUE = true;


    /**
     * The github URL of Dubbo Spring Boot
     */
    public static final String DUBBO_SPRING_BOOT_GITHUB_URL = "https://github.com/dubbo/dubbo-spring-boot-project";

    /**
     * The git URL of Dubbo Spring Boot
     */
    public static final String DUBBO_SPRING_BOOT_GIT_URL = "https://github.com/dubbo/dubbo-spring-boot-project.git";

    /**
     * The issues of Dubbo Spring Boot
     */
    public static final String DUBBO_SPRING_BOOT_ISSUES_URL = "https://github.com/dubbo/dubbo-spring-boot-project/issues";

    /**
     * The github URL of Dubbo
     */
    public static final String DUBBO_GITHUB_URL = "https://github.com/alibaba/dubbo";

    /**
     * The google group URL of Dubbo
     */
    public static final String DUBBO_GOOGLE_GROUP_URL = "http://groups.google.com/group/dubbo";

    /**
     * Filters Dubbo Properties from {@link ConfigurableEnvironment}
     *
     * @param environment {@link ConfigurableEnvironment}
     * @return Read-only SortedMap
     */
    public static SortedMap<String, Object> filterDubboProperties(ConfigurableEnvironment environment) {

        SortedMap<String, Object> dubboProperties = new TreeMap<>();

        Map<String, Object> properties = EnvironmentUtils.extractProperties(environment);

        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            String propertyName = entry.getKey();

            if (propertyName.startsWith(DUBBO_PREFIX + PROPERTY_NAME_SEPARATOR)) {
                dubboProperties.put(propertyName, entry.getValue());
            }

        }

        return Collections.unmodifiableSortedMap(dubboProperties);

    }

}
