package com.tour_web_app.validation.validator;


import com.tour_web_app.entity.Booking;
import com.tour_web_app.validation.annotation.ValidBookingCapacity;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBookingCapacityValidator implements ConstraintValidator<ValidBookingCapacity, Booking> {
    @Override
    public boolean isValid(Booking booking, ConstraintValidatorContext context) {
        if (booking.getTour() == null) return true;
        if (booking.getSeatsBooked() <= 0) return true;

        int available = booking.getTour().getAvailableSpots();
        int requested = booking.getSeatsBooked();

        return requested <= available;
    }
}
