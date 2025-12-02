package com.tour_web_app.controller;

import com.tour_web_app.aop.annotation.LogEvent;
import com.tour_web_app.aop.enums.EventType;
import com.tour_web_app.dto.SearchResult;
import com.tour_web_app.entity.Tour;
import com.tour_web_app.repository.TourRepository;
import com.tour_web_app.service.TourService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.*;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Conjunction;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@CrossOrigin()
@RestController
@RequestMapping("api/tours")
@AllArgsConstructor
public class TourController {
    private final TourService tourService;
    private final TourRepository tourRepository;

    @GetMapping()
    public ResponseEntity<SearchResult<Tour>> getAll(
            @Conjunction({
                    @Or(@Spec(path = "country", params = "country", spec = LikeIgnoreCase.class)),
                    @Or(@Spec(path = "city", params = "city", spec = LikeIgnoreCase.class)),
                    @Or(@Spec(path = "rating", params = "rating", spec = Equal.class)),
                    @Or(@Spec(path = "availableSpots", params = "spots", spec = GreaterThanOrEqual.class)),
                    @Or(@Spec(path = "price", params = "priceFrom", spec = GreaterThanOrEqual.class)),
                    @Or(@Spec(path = "price", params = "priceTo", spec = LessThanOrEqual.class)),
                    @Or(@Spec(path = "checkInDate", params = "dateFrom", spec = GreaterThanOrEqual.class)),
                    @Or(@Spec(path = "checkOutDate", params = "dateTo", spec = LessThanOrEqual.class))
            }) Specification<Tour> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(tourService.getAll(spec, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tour> getById(@PathVariable("id") long id) {
        Tour tour = tourService.getTourById(id);
        return ResponseEntity.ok(tour);
    }

    @PostMapping()
    @LogEvent(eventType = EventType.TOUR_CREATE)
    public ResponseEntity<Tour> create(@RequestBody @Valid Tour tour) {
        Tour createdTour = tourService.create(tour);

        return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @LogEvent(eventType = EventType.TOUR_UPDATE)
    public ResponseEntity<Tour> update(@RequestBody @Valid Tour tour, @PathVariable("id") long id) {
        Tour uodatedTour = tourService.update(tour, id);
        return ResponseEntity.ok(uodatedTour);
    }

    @DeleteMapping("/{id}")
    @LogEvent(eventType = EventType.TOUR_DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") long id) {
        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}



