package com.tour_web_app.dto.mapper;

import com.tour_web_app.dto.request.BookingRequest;
import com.tour_web_app.dto.response.BookingResponse;
import com.tour_web_app.entity.Booking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingResponse toBookingResponse(Booking booking);
    Booking toBookingEntity(BookingRequest bookingRequest);
}
