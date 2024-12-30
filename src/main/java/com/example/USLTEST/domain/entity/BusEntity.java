package com.example.USLTEST.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "buses")
@NoArgsConstructor
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bus_id")
    private Long busId;

    @NotBlank(message = "Driver name cannot be blank")
    @Column(name = "driver_name")
    private String driverName;

    @NotBlank(message = "Phone number cannot be blank")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "bus_number", unique = true)
    @NotBlank(message = "Bus number cannot be blank")
    private String busNumber;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "bus_model")
    private String busModel;

    @OneToOne(mappedBy = "bus")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private RouteEntity route;
}