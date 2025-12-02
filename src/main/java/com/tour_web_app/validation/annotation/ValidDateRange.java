package com.tour_web_app.validation.annotation;

import com.tour_web_app.validation.validator.ValidDateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidDateRangeValidator.class)
public @interface ValidDateRange {
    String message() default "Check-in date must be before check-out date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
