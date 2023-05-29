package com.example.springboot2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


@Configuration
@EnableSwagger2WebMvc
@Profile({"dev", "test"})
public class SwaggerConfig {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 这里一定要标注你controller的位置
                .apis(RequestHandlerSelectors.basePackage("com.example.springboot2.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api info
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("User Center")
                .description("User Center API Doc")
                .termsOfServiceUrl("https://github.com/Vizards8")
                .contact(new Contact("Vizards8", "https://github.com/Vizards8", "xxx@gmail.com"))
                .version("1.0")
                .build();
    }
}
