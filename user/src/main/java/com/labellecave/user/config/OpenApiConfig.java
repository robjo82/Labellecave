package com.labellecave.user.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${server.port:80}")
    private int serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        String baseUrl = (serverPort == 80 || serverPort == 443) ? "https://labellecave-user.robin-joseph.fr" : "http://localhost:" + serverPort;

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        ))
                .info(new Info()
                        .title("User API")
                        .version("1.0")
                        .description("User API v1.0")
                )
                .servers(new ArrayList<>(List.of(
                        new Server().url(baseUrl)
                )));
    }

}
