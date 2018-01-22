package org.hongxi.whatsmars.spring.boot.config;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.SqlUtilConfig;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean
    public Interceptor pageHelper() {
        PageHelper pageHelper = new PageHelper();
        SqlUtilConfig sqlUtilConfig = new SqlUtilConfig();
        sqlUtilConfig.setDialect("mysql");
        sqlUtilConfig.setOffsetAsPageNum(true);
        sqlUtilConfig.setRowBoundsWithCount(true);
        sqlUtilConfig.setPageSizeZero(true);
        sqlUtilConfig.setReasonable(false);
        sqlUtilConfig.setSupportMethodsArguments(false);
        pageHelper.setSqlUtilConfig(sqlUtilConfig);
        return pageHelper;
    }
}
