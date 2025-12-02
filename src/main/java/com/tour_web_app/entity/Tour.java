package com.tour_web_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.tour_web_app.validation.annotation.NonNegativePrice;
import com.tour_web_app.validation.annotation.ValidCapacity;
import com.tour_web_app.validation.annotation.ValidDateRange;
import com.tour_web_app.validation.annotation.ValidHotelRating;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ValidDateRange
@ValidCapacity
@Table(name = "Tour")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String city;

    @Column(name="check_in_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate checkInDate;

    @Column(name="check_out_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    private LocalDate checkOutDate;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @Column(name = "available_spots")
    private int availableSpots;

    @NonNegativePrice
    private double price;
    private String hotel;

    @ValidHotelRating
    private int rating;
    private String description;

    @Column(name = "images_url", columnDefinition = "text[]")
    private String[] imagesUrl;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<Comment> comments;
}
