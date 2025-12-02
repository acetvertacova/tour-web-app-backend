package com.tour_web_app.validation.validator;

import com.tour_web_app.validation.annotation.ValidHotelRating;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidHotelRatingValidator implements ConstraintValidator<ValidHotelRating, Integer> {
    @Override
    public boolean isValid(Integer rating, ConstraintValidatorContext context) {
        if (rating == null) {
            return true;
        }
        return rating >= 1 && rating <= 5;
    }
}
