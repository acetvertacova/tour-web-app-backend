package com.tour_web_app.validation.annotation;

import com.tour_web_app.validation.validator.ValidCapacityValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCapacityValidator.class)
public @interface ValidCapacity {
    String message() default "Max capacity cannot be less than available spots.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
