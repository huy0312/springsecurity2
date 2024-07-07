package com.huynguyen.springsecurity2.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class CustomErrorPageConfig {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return registry -> registry.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500"),
                new ErrorPage(HttpStatus.SERVICE_UNAVAILABLE, "/error-page/503"),
                new ErrorPage(HttpStatus.FORBIDDEN,"/error-page/403")
        );
    }
}