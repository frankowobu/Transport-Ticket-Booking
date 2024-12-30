package com.example.USLTEST.domain.DTO;

import com.example.USLTEST.domain.entity.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDto {
    private Long id;
    @NotBlank(message = "Seat number is required.")
    private String seatNumber;
    @Positive(message = "Price must be a positive value.")
    private Double price;
    @FutureOrPresent(message = "Departure day and time cannot be in the past.")
    private LocalDateTime departureDay;
    @FutureOrPresent(message = "Booking date and time cannot be in the past.")
    private LocalDateTime bookingDateTime;
    @Future(message = "Cancellation time must be in the future.")
    private LocalDateTime cancellationTime;
    private Status status;
    private Long userId;
    private Long routeId;
    private Long busId;
}
