package org.example.restfuldemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restful Demo API")
                        .version("1.0.0")
                        .description("API documentation for the Restful Demo project")
                        .contact(new Contact()
                                .name("Developer Support")
                                .email("support@example.org")
                                .url("https://example.org")));
    }
}
