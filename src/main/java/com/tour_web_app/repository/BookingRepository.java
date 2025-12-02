package com.tour_web_app.repository;

import com.tour_web_app.entity.Booking;
import com.tour_web_app.entity.Tour;
import com.tour_web_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User user);
    boolean existsByUserAndTour(User user, Tour tour);
}
