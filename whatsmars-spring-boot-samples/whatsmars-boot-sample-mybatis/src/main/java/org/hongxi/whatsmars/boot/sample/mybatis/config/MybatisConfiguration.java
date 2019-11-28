package org.hongxi.whatsmars.boot.sample.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shenhongxi on 2018/12/9.
 */
@Configuration
public class MybatisConfiguration {

    @MapperScan(basePackages = "org.hongxi.whatsmars.boot.sample.mybatis.dao.user", sqlSessionTemplateRef = "userSqlSessionTemplate")
    private class userMapperScan {}

    @MapperScan(basePackages = "org.hongxi.whatsmars.boot.sample.mybatis.dao.trade", sqlSessionTemplateRef = "tradeSqlSessionTemplate")
    private class tradeMapperScan {}

    @Bean("userSqlSessionTemplate")
    public SqlSessionTemplate userSqlSessionTemplate(
            @Qualifier("userSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("tradeSqlSessionTemplate")
    public SqlSessionTemplate tradeSqlSessionTemplate(
            @Qualifier("tradeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(
            @Qualifier("userDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("org.hongxi.whatsmars.boot.sample.mybatis.domain");
        String[] mapperLocations = {"classpath:mapper/*.xml"};
        factory.setMapperLocations(resolveMapperLocations(mapperLocations));
        return factory.getObject();
    }

    @Bean("tradeSqlSessionFactory")
    public SqlSessionFactory tradeSqlSessionFactory(
            @Qualifier("tradeDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("org.hongxi.whatsmars.boot.sample.mybatis.domain");
        String[] mapperLocations = {"classpath:mapper/*.xml"};
        factory.setMapperLocations(resolveMapperLocations(mapperLocations));
        return factory.getObject();
    }

    @Bean("userDatasource")
    @ConfigurationProperties(prefix = "user.datasource")
    public DataSource userDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("tradeDatasource")
    @ConfigurationProperties(prefix = "trade.datasource")
    public DataSource tradeDatasource() {
        return DataSourceBuilder.create().build();
    }

    private Resource[] resolveMapperLocations(String[] mapperLocations) {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }
}
