package com.tinqin.library.book.core.aspects.loggingaspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoggedProcessing {

    LoggingLevel logLevel() default LoggingLevel.DEFAULT;
}
