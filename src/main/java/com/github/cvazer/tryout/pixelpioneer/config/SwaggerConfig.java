package com.github.cvazer.tryout.pixelpioneer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfo("Pixel Pioneer - Tryout - Yan F - Rest APIs",
                "Tryout task implementation",
                "1.0",
                "Terms of service",
                new Contact("Yan Frankovski", null, "zhirnitoni@gmail.com"),
                "No License",
                "https://choosealicense.com/no-permission/",
                List.of()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION, "JWT (Dont forget \"Bearer\" part!", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        return List.of(
                new SecurityReference(AUTHORIZATION, new AuthorizationScope[]{
                        new AuthorizationScope("global", "accessEverything")
                }));
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

}
