package com.ivmiku.limiter.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Aurora
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Limiter {
    String key() default "";
    int time();
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
    int num();
}
