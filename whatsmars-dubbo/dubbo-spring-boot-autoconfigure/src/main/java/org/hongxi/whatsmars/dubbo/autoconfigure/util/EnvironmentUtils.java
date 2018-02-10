
package org.hongxi.whatsmars.dubbo.autoconfigure.util;

import org.springframework.core.env.*;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class EnvironmentUtils {

    /**
     * Extras The properties from {@link ConfigurableEnvironment}
     *
     * @param environment {@link ConfigurableEnvironment}
     * @return Read-only Map
     */
    public static Map<String, Object> extractProperties(ConfigurableEnvironment environment) {
        return Collections.unmodifiableMap(doExtraProperties(environment));
    }

//    /**
//     * Gets {@link PropertySource} Map , the {@link PropertySource#getName()} as key
//     *
//     * @param environment {@link ConfigurableEnvironment}
//     * @return Read-only Map
//     */
//    public static Map<String, PropertySource<?>> getPropertySources(ConfigurableEnvironment environment) {
//        return Collections.unmodifiableMap(doGetPropertySources(environment));
//    }

    private static Map<String, Object> doExtraProperties(ConfigurableEnvironment environment) {

        Map<String, Object> properties = new LinkedHashMap<>(); // orderly

        Map<String, PropertySource<?>> map = doGetPropertySources(environment);

        for (PropertySource<?> source : map.values()) {

            if (source instanceof EnumerablePropertySource) {

                EnumerablePropertySource propertySource = (EnumerablePropertySource) source;

                String[] propertyNames = propertySource.getPropertyNames();

                if (ObjectUtils.isEmpty(propertyNames)) {
                    continue;
                }

                for (String propertyName : propertyNames) {

                    if (!properties.containsKey(propertyName)) { // put If absent
                        properties.put(propertyName, propertySource.getProperty(propertyName));
                    }

                }

            }

        }

        return properties;

    }

    private static Map<String, PropertySource<?>> doGetPropertySources(ConfigurableEnvironment environment) {
        Map<String, PropertySource<?>> map = new LinkedHashMap<String, PropertySource<?>>();
        MutablePropertySources sources = environment.getPropertySources();
        for (PropertySource<?> source : sources) {
            extract("", map, source);
        }
        return map;
    }

    private static void extract(String root, Map<String, PropertySource<?>> map,
                                PropertySource<?> source) {
        if (source instanceof CompositePropertySource) {
            for (PropertySource<?> nest : ((CompositePropertySource) source)
                    .getPropertySources()) {
                extract(source.getName() + ":", map, nest);
            }
        } else {
            map.put(root + source.getName(), source);
        }
    }

}
