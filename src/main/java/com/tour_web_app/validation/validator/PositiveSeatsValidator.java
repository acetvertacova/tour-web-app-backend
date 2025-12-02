package com.tour_web_app.validation.validator;

import com.tour_web_app.validation.annotation.PositiveSeats;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PositiveSeatsValidator implements ConstraintValidator<PositiveSeats, Integer> {
    @Override
    public boolean isValid(Integer seatsBooked, ConstraintValidatorContext context) {
        return seatsBooked != null && seatsBooked > 0;
    }
}
