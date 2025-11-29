package com.tour_web_app.controller;

import com.tour_web_app.entity.Tour;
import com.tour_web_app.repository.TourRepository;
import com.tour_web_app.service.TourService;
import com.tour_web_app.specification.TourSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("api")
@AllArgsConstructor
public class TourController {
    private final TourService tourService;
    private final TourRepository tourRepository;

    @GetMapping("/tours")
    public List<Tour> getAll() {
        return tourService.getAll();
    }

    @GetMapping("/tours/{id}")
    public Tour getById(@PathVariable("id") long id) {
        return tourService.getTourById(id);
    }

    @PostMapping("/tours")
    public Tour create(@RequestBody Tour tour) {
        return tourService.create(tour);
    }

    @PutMapping("/tours/{id}")
    public Tour update(@RequestBody Tour tour, @PathVariable("id") long id) {
        return tourService.update(tour, id);
    }

    @DeleteMapping("/tours/{id}")
    public void deleteById(@PathVariable("id") long id) {
        tourService.deleteById(id);
    }

    @GetMapping("tours/search")
    public List<Tour> searchTours( @RequestParam(value = "country", required = false) String country,
                                   @RequestParam(value = "priceFrom", required = false) String priceFrom,
                                   @RequestParam(value = "priceTo", required = false) String priceTo,
                                   @RequestParam(value = "rating", required = false) Integer rating,
                                   @RequestParam(value = "availableSpots", required = false) Integer availableSpots,
                                   @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
                                   @RequestParam(value = "dateTo", required = false) LocalDate dateTo){
        Specification<Tour> spec = Specification.where(null);

        if(country != null) spec = spec.and(TourSpecification.hasCountry(country));
        if (priceFrom != null) spec = spec.and(TourSpecification.minPrice(priceFrom));
        if (priceTo != null) spec = spec.and(TourSpecification.maxPrice(priceTo));
        if(rating != null) spec = spec.and(TourSpecification.hasRating(rating));
        if(availableSpots != null) spec = spec.and(TourSpecification.hasAvailableSpots(availableSpots));
        if(dateFrom != null && dateTo != null) spec = spec.and(TourSpecification.datesBetween(dateFrom, dateTo));

        return tourRepository.findAll(spec);
    }
}



