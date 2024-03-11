package com.example.mall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                // 拦截所有请求
                .addPathPatterns("/**")
                // 不拦截的请求
                .excludePathPatterns("/error", "/user/login", "/user/register", "/categories", "/products", "/products/*");
    }
}
