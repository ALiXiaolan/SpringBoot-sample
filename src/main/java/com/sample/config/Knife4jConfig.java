/*
package com.sample.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

*/
/**
 * @author xiaolan
 * @version 1.0.0-SNAPSHOT
 * @date 2021/11/22 下午2:12
 *//*

@Configuration
@EnableSwagger2
public class Knife4jConfig {

    public Docket defaultApi2() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("# swagger-bootstrap-ui-demo RESTful APIs")
                        .termsOfServiceUrl("http://www.xx.com/")
                        .version("1.0")
                        .build())
                .groupName("2.X版本")
                .select()
                //controller包扫描路径
                .apis(RequestHandlerSelectors.basePackage("com"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
*/
