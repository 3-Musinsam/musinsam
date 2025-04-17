package com.musinsam.orderservice.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(
    info = @Info(
        title = "ORDER API",
        version = "v1",
        description = "ORDER API 입니다."
    )
)
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {

    return new OpenAPI()
        .addServersItem(new Server().url("/"));
  }
}
