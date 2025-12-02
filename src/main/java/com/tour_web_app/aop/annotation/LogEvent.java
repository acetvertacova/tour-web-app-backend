package com.tour_web_app.aop.annotation;

import com.tour_web_app.aop.enums.EventType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogEvent {
    EventType eventType() default EventType.LOGIN;
    String description() default "";
}
