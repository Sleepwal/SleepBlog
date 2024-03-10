//package com.sleep.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * Swagger2配置
// *
// * @author SleepWalker
// * @version 1.0
// * @date 2023/5/14 12:14
// */
//
//@Configuration
//
//public class SwaggerConfig {
//    /**
//     * 配置Swagger3相关的bean
//     */
//    @Bean
//    public Docket docket(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo()).enable(true)
//                .select()
//                //添加swagger接口提取范围,修改成指向你的controller包
//                .apis(RequestHandlerSelectors.basePackage("com.sleep"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    /**
//     * 此处主要是API文档页面显示信息
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("SleepArsenal API") // 标题
//                .description("SleepArsenal") // 描述
//                .version("1.0") // 版本
//                .build();
//    }
//}
//
//
