package com.tour_web_app.service;

import com.tour_web_app.dto.mapper.BookingMapper;
import com.tour_web_app.dto.request.BookingRequest;
import com.tour_web_app.dto.response.BookingResponse;
import com.tour_web_app.entity.Booking;
import com.tour_web_app.entity.Tour;
import com.tour_web_app.entity.User;
import com.tour_web_app.exception.CreateFailedException;
import com.tour_web_app.exception.NotFoundException;
import com.tour_web_app.repository.BookingRepository;
import com.tour_web_app.repository.TourRepository;
import com.tour_web_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    @Transactional
    public BookingResponse booking(BookingRequest request, String username){
        Tour tour = tourRepository.findById(request.tourId()).orElseThrow(()-> new NotFoundException("Tour not found"));

        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Tour not found"));

        if (bookingRepository.existsByUserAndTour(user, tour)) {
            throw new CreateFailedException("You have already booked this tour");
        }

        tour.setAvailableSpots(tour.getAvailableSpots() - request.seatsBooked());
        tourRepository.save(tour);

        Booking booking = bookingRepository.save(bookingMapper.toBookingEntity(request));
        return bookingMapper.toBookingResponse(booking);
    }

    public List<BookingResponse> getAllBookingsOfUser(String username){
       User user = userRepository.findByUsername(username).orElseThrow();
       List<Booking> bookings = bookingRepository.findAllByUser(user);

       return bookings.stream()
               .map(bookingMapper::toBookingResponse)
               .toList();
    }

    @Transactional
    public BookingResponse cancel(Long bookingId, String username){
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Tour not found"));

        if (!booking.getUser().getUsername().equals(username)) {
            throw new CreateFailedException("You can cancel only your bookings");
        }

        Tour tour = booking.getTour();
        tour.setAvailableSpots(tour.getAvailableSpots() + booking.getSeatsBooked());
        tourRepository.save(tour);

        bookingRepository.delete(booking);
        return bookingMapper.toBookingResponse(booking);
    }
}
