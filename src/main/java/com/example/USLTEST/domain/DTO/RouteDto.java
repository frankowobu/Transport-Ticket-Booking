package com.example.USLTEST.domain.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class RouteDto {
    private Long routeId;  // Matches the entity's routeId

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotBlank(message = "Departure date is required")
    private LocalDate departureDate;

    @NotBlank(message = "Departure time is required")
    private LocalTime departureTime;

    @NotBlank(message = "Duration is required")
    private String duration;

    @Positive(message = "Price must be positive")
    private Double price;

    private Set<Long> busIds;      // Represents the IDs of buses associated with the route
    private Set<Long> ticketIds;   // Represents the IDs of tickets associated with the route
}
