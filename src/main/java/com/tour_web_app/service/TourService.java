package com.tour_web_app.service;

import com.tour_web_app.entity.Tour;
import com.tour_web_app.repository.TourCacheRepository;
import com.tour_web_app.repository.TourRepository;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourCacheRepository tourCacheRepository;

    public List<Tour> getAll() {
        List<Tour> tours = StreamSupport.stream(tourRepository
                        .findAll()
                        .spliterator(), false)
                .toList();
        return tours;
    }

    public Tour getTourById(Long id) {
        Tour cachedTour = tourCacheRepository.findById(id);
        if (cachedTour != null) {
            return cachedTour;
        }
        Tour dbTour = tourRepository.findById(id).orElseThrow(() -> new ValidationException("Tour not found"));
        tourCacheRepository.save(dbTour);
        return dbTour;
    }

    public Tour create(Tour tour) {
        Optional<Tour> existingTour = tourRepository.findByCheckInDateAndCheckOutDateAndCountry(tour.getCheckInDate(), tour.getCheckOutDate(), tour.getCountry());

        if (existingTour.isPresent()) {
            throw new ValidationException("Tour with the same date and destination has been already created!");
        }

        if (tour.getMaxCapacity() < tour.getAvailableSpots()) {
            throw new ValidationException("Max capacity cannot be less than available spots.");
        }

        Tour dbTour = tourRepository.save(tour);
        tourCacheRepository.save(dbTour);
        return dbTour;
    }

    public Tour update(Tour tour, long id) {
        Tour tourToUpdate = tourRepository.findById(id).orElseThrow(() -> new ValidationException("Tour not found"));

        Tour updatedTour = tour.builder()
                .id(tourToUpdate.getId())
                .name(tour.getName())
                .country(tour.getCountry())
                .city(tour.getCity())
                .maxCapacity(tour.getMaxCapacity())
                .availableSpots(tour.getAvailableSpots())
                .checkInDate(tour.getCheckInDate())
                .checkOutDate(tour.getCheckOutDate())
                .price(tour.getPrice())
                .hotel(tour.getHotel())
                .rating(tour.getRating())
                .description(tour.getDescription())
                .imagesUrl(tour.getImagesUrl())
                .build();
        Tour saved = tourRepository.save(updatedTour);

        Tour cachedTour = tourCacheRepository.findById(id);
        if (cachedTour != null) {
            tourCacheRepository.save(saved);
        }

        return saved;
    }

    public void deleteById(long id) {
        // Invalidate cache on delete
        tourCacheRepository.deleteById(id);
        tourRepository.deleteById(id);
    }
}
