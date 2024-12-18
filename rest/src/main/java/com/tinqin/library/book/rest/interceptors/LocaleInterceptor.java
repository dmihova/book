package com.tinqin.library.book.rest.interceptors;

import com.tinqin.library.book.rest.models.LocaleHeader;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class LocaleInterceptor implements HandlerInterceptor {
    private final LocaleHeader localeHeader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String locale = Optional
                .ofNullable(request.getHeader("locale"))
                .orElse("en");

        localeHeader.setLocale(locale);

        return true;
    }
}
