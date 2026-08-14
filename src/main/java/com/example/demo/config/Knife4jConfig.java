package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("图书管理系统 API 文档")
                        .version("v1.6")
                        .description("基于 Spring Boot + MyBatis + Redis + Spring Security + JWT 的图书管理后端系统")
                        .contact(new Contact()
                                .name("图书馆管理系统")
                                .email("admin@example.com")));
    }
}
