package com.tinqin.library.book.core.aspects.loggingaspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
@Order(2)
public class LoggingAspect {

    @Around("@annotation(com.tinqin.library.book.core.aspects.loggingaspect.LoggedProcessing)")
    public Object logArguments(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        LoggedProcessing loggedProcessing = AnnotationUtils.findAnnotation(signature.getMethod(), LoggedProcessing.class);

        String className = signature.getMethod().getDeclaringClass().getName();
        String methodName = signature.getMethod().getName();

        log.info("About ot execute method: {}.{}()", className, methodName);
        if (loggedProcessing.logLevel() == LoggingLevel.DEBUG) {
            String processorInput = joinPoint.getArgs()[0].toString();

            log.info("Method arguments: {}", processorInput);
        }

        return joinPoint.proceed();
    }
}
