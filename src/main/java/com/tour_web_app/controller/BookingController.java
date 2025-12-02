package com.tour_web_app.controller;

import com.tour_web_app.dto.request.BookingRequest;
import com.tour_web_app.dto.response.BookingResponse;
import com.tour_web_app.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping()
    public ResponseEntity<BookingResponse> book(@RequestBody BookingRequest request, Authentication authentication) {
        String username = authentication.getName();
        BookingResponse bookingResponse = bookingService.booking(request, username);

        return ResponseEntity.ok().body(bookingResponse);
    }

    @GetMapping()
    public ResponseEntity<List<BookingResponse>> getAllBookingsOfUser(Authentication authentication){
        String username = authentication.getName();
        List<BookingResponse> bookings = bookingService.getAllBookingsOfUser(username);

        return ResponseEntity.ok().body(bookings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> cancel(@PathVariable("id") Long BookingId, Authentication authentication) {
        String username = authentication.getName();
        BookingResponse canceled = bookingService.cancel(BookingId, username);

        return ResponseEntity.ok().body(canceled);
    }
}
