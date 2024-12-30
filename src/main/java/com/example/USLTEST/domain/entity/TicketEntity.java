package com.example.USLTEST.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tickets")
@NoArgsConstructor
public class TicketEntity {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private BusEntity bus;

    @Column(name = "seat_number")
    private String seatNumber;

    @Column(name = "departure_day")
    private LocalDate departureDay;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "booking_date_time", nullable = false)
    private LocalDateTime bookingDateTime = LocalDateTime.now();

    @Column(name = "cancellation_time")
    private LocalDateTime cancellationTime;

}
