package com.bank.balance.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bank API Recarga Pay")
                        .description("API for managing bank accounts developed for Claudemir Ramos Ferreira")
                        .version("1.0.0"));
    }
}
