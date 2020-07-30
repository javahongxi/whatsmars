package org.hongxi.whatsmars.boot.sample.actuator;

import org.springframework.boot.actuate.autoconfigure.condition.ConditionsReportEndpoint;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.beans.BeansEndpoint;
import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint;
import org.springframework.boot.actuate.env.EnvironmentEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.web.mappings.MappingsEndpoint;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by shenhongxi on 2020/7/30.
 */
public class StandardWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Override  
    public void configure(WebSecurity web) throws Exception {
        // 普通web资源
    }
  
    @Override  
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()  
                .requestMatchers(EndpointRequest.to(
                        MappingsEndpoint.class,
                        EnvironmentEndpoint.class,
                        ConditionsReportEndpoint.class,
                        ConfigurationPropertiesReportEndpoint.class,
                        BeansEndpoint.class,
                        LoggersEndpoint.class,
                        MetricsEndpoint.class))
                .hasRole("application")
                .anyRequest()
                .permitAll()
                .and()
                .httpBasic();  
  
    }  
}  