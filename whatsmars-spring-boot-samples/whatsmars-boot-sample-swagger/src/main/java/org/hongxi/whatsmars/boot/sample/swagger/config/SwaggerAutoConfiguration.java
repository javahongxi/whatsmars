package org.hongxi.whatsmars.boot.sample.swagger.config;

import com.fasterxml.classmate.TypeResolver;
import org.hongxi.whatsmars.boot.sample.swagger.Application;
import org.hongxi.whatsmars.boot.sample.swagger.common.ProfileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Profile({"dev", "test"})
@EnableOpenApi
@Configuration
public class SwaggerAutoConfiguration {

    @Autowired
    private TypeResolver typeResolver;
    @Autowired
    private Environment env;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("mars-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage(Application.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)), typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .host(host())
                .apiInfo(apiInfo());
    }

    private String host() {
        String port = env.getProperty("server.port", "8080");
        if (ProfileUtils.isDev()) {
            return "localhost:" + port;
        }
        return "test.hongxi.org";
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Mars API")
                .description("用于调试 Mars RestAPI,只在test/dev中放开,prod中关闭")
                .version("0.1")
                .build();
    }
}