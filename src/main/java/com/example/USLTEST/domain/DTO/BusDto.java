package com.example.USLTEST.domain.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BusDto {
    private Long busId;
    private String busNumber;
    private String busModel;
    private String driverName;
    private String phoneNumber;
    @NotNull(message = "Seating capacity cannot be null")
    @Positive(message = "Seating capacity must be positive")
    private int capacity;
    private Long userId;  // Reference to the associated user
    private Long routeId; // Reference to the associated route
}
