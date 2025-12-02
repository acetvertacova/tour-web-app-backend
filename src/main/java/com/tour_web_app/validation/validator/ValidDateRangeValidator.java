package com.tour_web_app.validation.validator;

import com.tour_web_app.entity.Tour;
import com.tour_web_app.validation.annotation.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, Tour> {
    @Override
    public boolean isValid(Tour tour, ConstraintValidatorContext context) {
        if (tour.getCheckInDate() == null || tour.getCheckOutDate() == null) return true;

        return tour.getCheckInDate().isBefore(tour.getCheckOutDate());
    }
}
