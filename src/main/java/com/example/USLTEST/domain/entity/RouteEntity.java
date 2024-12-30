package com.example.USLTEST.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Entity
@Table(name = "routes")
@NoArgsConstructor
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long routeId;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Positive(message = "Price must be positive")
    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "duration")
    private String duration;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<BusEntity> buses;

    @OneToMany(mappedBy = "route", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<TicketEntity> tickets;
}
