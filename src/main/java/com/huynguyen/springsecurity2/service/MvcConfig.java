package com.huynguyen.springsecurity2.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path brandUploadDir = Paths.get("D:/uploads/");
        String brandUploadDirPath = brandUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:avatar\\");

    }
}
