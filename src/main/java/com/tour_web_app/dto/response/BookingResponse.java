package com.tour_web_app.dto.response;

import com.tour_web_app.entity.Tour;

public record BookingResponse(Long id, Tour tour, int seatsBooked) {
}
