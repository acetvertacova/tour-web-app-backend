package com.tour_web_app.validation.validator;

import com.tour_web_app.validation.annotation.NonNegativePrice;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NonNegativePriceValidator implements ConstraintValidator<NonNegativePrice, Double> {
    @Override
    public boolean isValid(Double price, ConstraintValidatorContext context) {
        return price != null && price > 0;
    }
}
