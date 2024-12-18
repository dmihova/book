package com.tinqin.library.book.rest.config;

import com.tinqin.library.book.rest.interceptors.LocaleInterceptor;

import com.tinqin.library.book.rest.interceptors.LoggingInterceptor;
import com.tinqin.library.book.rest.models.LocaleHeader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    @Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public LocaleHeader getLocaleHeader() {
        return new LocaleHeader();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
        registry.addInterceptor(new LocaleInterceptor(getLocaleHeader()));
    }
}
