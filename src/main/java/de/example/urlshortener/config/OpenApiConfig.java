package de.example.urlshortener.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "URL Shortener", description = "RESTful web service"),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig {
}
