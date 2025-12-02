package com.tour_web_app.validation.annotation;

import com.tour_web_app.validation.validator.ValidBookingCapacityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidBookingCapacityValidator.class)
public @interface ValidBookingCapacity {
    String message() default "Not enough available spots for this booking";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
