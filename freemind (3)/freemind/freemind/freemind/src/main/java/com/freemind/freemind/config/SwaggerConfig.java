package com.freemind.freemind.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("API en administración")
            .version("1.1")
            .description("Con la API se incluye la creación, actualización y eliminación de EStados")
        );
    }
}

//VER ESTO!!