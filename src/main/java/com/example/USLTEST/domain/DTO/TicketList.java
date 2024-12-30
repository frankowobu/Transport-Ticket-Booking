package com.example.USLTEST.domain.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TicketList {
    private String passengerName;

    private String driverName;

    private String busNumber;

    private String origin;

    private String destination;

    private String departureDate;

    @FutureOrPresent(message = "Booking date and time cannot be in the past.")
    private LocalDateTime bookingDateTime;

    @Future(message = "Cancellation time must be in the future.")
    private LocalDateTime cancellationTime;

    private String estimatedDuration;

    private String seatNumber;

    private double price;
}