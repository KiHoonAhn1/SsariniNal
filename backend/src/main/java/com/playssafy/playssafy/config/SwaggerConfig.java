package com.playssafy.playssafy.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// ?????
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    private String version = "v2";
    private String title = "PLAYSSAFY REST API";
    private String description = "PLAYSSAFY REST API Docs";

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .version(version)
                .build();
    }
}
