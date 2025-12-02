package com.tour_web_app.entity;

import com.tour_web_app.validation.annotation.PositiveSeats;
import com.tour_web_app.validation.annotation.ValidBookingCapacity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Setter
@Getter
@Entity
@Builder
@ValidBookingCapacity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @PositiveSeats
    @Column(name = "seats_booked")
    private Integer seatsBooked;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Tour tour;
}
