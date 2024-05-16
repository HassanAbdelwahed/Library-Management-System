package com.example.LibraryManagementSystem.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI SwaggerApi() {
        return new OpenAPI().info(new Info().title("Spring Doc").version("1.0.0").description("Spring Doc"));
    }

}
