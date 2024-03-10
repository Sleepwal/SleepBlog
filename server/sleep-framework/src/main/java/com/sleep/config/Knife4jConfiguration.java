package com.sleep.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;

/**
 * @author SleepWalker
 * @version 1.0
 * @date 2023/7/30 0:33
 */

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("# SleepBlog RESTful APIs")
                        .termsOfServiceUrl("http://www.sleepblog.com/")
                        .contact(new Contact("SleepWalker", "sleep.com", "sleep@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("1.X版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.sleep"))
                .paths(PathSelectors.any())
                .build();
//                .securityContexts(securityContexts())
//                .securitySchemes(securitySchemes());
        return docket;
    }

    private List<SecurityContext> securityContexts() {
        return List.of(SecurityContext.builder()
                        .securityReferences(List.of(
                                new SecurityReference("token",
                                        new AuthorizationScope[]{
                                                new AuthorizationScope("global", "")
                                        })
                        ))
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
    }

    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("token", "token", In.HEADER.toValue());
        return List.of(apiKey);
    }
}
