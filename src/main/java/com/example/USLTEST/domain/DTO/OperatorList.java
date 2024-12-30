package com.example.USLTEST.domain.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OperatorList {

    private Long operatorId;

    private String driverName;

    private String busNumber;

    private String busModel;

	@Positive(message = "Seating capacity must be positive")
    private int capacity;

    private Long routeId;

    private String origin;

    private String destination;

    private String phoneNumber;

}
