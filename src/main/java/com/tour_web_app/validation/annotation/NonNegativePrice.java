package com.tour_web_app.validation.annotation;

import com.tour_web_app.validation.validator.NonNegativePriceValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NonNegativePriceValidator.class)
public @interface NonNegativePrice {
    String message() default "Price cannot be negative.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
