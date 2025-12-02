package com.tour_web_app.service;

import com.tour_web_app.dto.SearchResult;
import com.tour_web_app.entity.Tour;
import com.tour_web_app.exception.CreateFailedException;
import com.tour_web_app.exception.NotFoundException;
import com.tour_web_app.repository.TourCacheRepository;
import com.tour_web_app.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourCacheRepository tourCacheRepository;

    public SearchResult<Tour> getAll(Specification<Tour> spec, Pageable pageable) {
        Page<Tour> page = tourRepository.findAll(spec, pageable);
        return new SearchResult<>(page);
    }

    public Tour getTourById(Long id) {
        Tour cachedTour = tourCacheRepository.findById(id);
        if (cachedTour != null) {
            return cachedTour;
        }
        Tour dbTour = tourRepository.findById(id).orElseThrow(() -> new NotFoundException("Tour not found"));
        tourCacheRepository.save(dbTour);
        return dbTour;
    }

    public Tour create(Tour tour) {
        Optional<Tour> existingTour = tourRepository.findByCheckInDateAndCheckOutDateAndCountry(tour.getCheckInDate(), tour.getCheckOutDate(), tour.getCountry());

        if (existingTour.isPresent()) {
            throw new CreateFailedException("Tour with the same date and destination has been already created!");
        }

        Tour dbTour = tourRepository.save(tour);
        tourCacheRepository.save(dbTour);
        return dbTour;
    }

    public Tour update(Tour tour, long id) {
        Tour tourToUpdate = tourRepository.findById(id).orElseThrow(() -> new NotFoundException("Tour not found"));

        Tour updatedTour = Tour.builder()
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
