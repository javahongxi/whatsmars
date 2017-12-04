package com.whatsmars.spring.boot.config;

import com.fasterxml.classmate.TypeResolver;
import com.whatsmars.spring.boot.App;
import com.whatsmars.spring.boot.common.ProfileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Profile({"dev", "test"})
@ConditionalOnClass(EnableSwagger2.class)
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;
    @Autowired
    private Environment env;

    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("business-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage(App.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)), typeResolver.resolve(WildcardType.class)))
//                .enableUrlTemplating(true)
                .useDefaultResponseMessages(true)
                .forCodeGeneration(false)
                .host(host())
                .apiInfo(apiInfo());
    }


    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",// url
                "none",       // docExpansion          => none | list
                "alpha",      // apiSorter             => alpha
                "schema",     // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                false,        // enableJsonEditor      => true | false
                true);        // showRequestHeaders    => true | false
    }

    private String host() {
        String port = env.getProperty("server.port");
        if (ProfileUtils.isDev()) {
            return "localhost:" + port;
        }
        return "test.toutiao.im";
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Toutiao API")
                .description("用于调试Toutiao RestAPI,只在test/dev中放开,prod中关闭")
                .version("0.1")
                .build();

    }
}