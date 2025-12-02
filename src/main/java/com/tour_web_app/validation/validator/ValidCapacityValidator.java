package com.tour_web_app.validation.validator;

import com.tour_web_app.entity.Tour;
import com.tour_web_app.validation.annotation.ValidCapacity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidCapacityValidator implements ConstraintValidator<ValidCapacity, Tour> {

    @Override
    public boolean isValid(Tour tour, ConstraintValidatorContext context) {
        return tour.getAvailableSpots() <= tour.getMaxCapacity();
    }
}
