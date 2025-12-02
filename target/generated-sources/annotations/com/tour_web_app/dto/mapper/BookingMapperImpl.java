package com.tour_web_app.dto.mapper;

import com.tour_web_app.dto.request.BookingRequest;
import com.tour_web_app.dto.response.BookingResponse;
import com.tour_web_app.entity.Booking;
import com.tour_web_app.entity.Tour;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T20:22:52+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class BookingMapperImpl implements BookingMapper {

    @Override
    public BookingResponse toBookingResponse(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        Long id = null;
        Tour tour = null;
        int seatsBooked = 0;

        id = booking.getId();
        tour = booking.getTour();
        if ( booking.getSeatsBooked() != null ) {
            seatsBooked = booking.getSeatsBooked();
        }

        BookingResponse bookingResponse = new BookingResponse( id, tour, seatsBooked );

        return bookingResponse;
    }

    @Override
    public Booking toBookingEntity(BookingRequest bookingRequest) {
        if ( bookingRequest == null ) {
            return null;
        }

        Booking.BookingBuilder booking = Booking.builder();

        booking.seatsBooked( bookingRequest.seatsBooked() );

        return booking.build();
    }
}
