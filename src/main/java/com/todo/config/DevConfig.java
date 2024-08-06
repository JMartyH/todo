package com.todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DevConfig implements WebMvcConfigurer {


    public void addCorsMappings(CorsRegistry registry) {
        // Allow everything for development (not recommended for production)
        registry.addMapping("/**")
                .allowedOrigins("*") // Or specify your frontend URL (e.g., "http://localhost:5173")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
