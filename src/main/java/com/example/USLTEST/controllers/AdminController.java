package com.example.USLTEST.controllers;

import com.example.USLTEST.domain.DTO.BusDto;
import com.example.USLTEST.domain.DTO.RouteDto;
import com.example.USLTEST.service.BusService;
import com.example.USLTEST.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BusService busService;

    private final RouteService routeService;

    @PostMapping("/create-route")
    public ResponseEntity<RouteDto> createRoute(@RequestBody RouteDto routeDto) {
        return routeService.createRoute(routeDto);
    }

    @PutMapping("/update-route/{id}")
    public ResponseEntity<RouteDto> updateRoute(@RequestBody RouteDto routeDto, @PathVariable Long id) {
        return routeService.updateRoute(routeDto, id);
    }

    @PostMapping("/create-bus")
    public ResponseEntity<BusDto> createBus(@RequestBody BusDto busDto) {
        return busService.createBus(busDto);
    }

    @PutMapping("/update-bus/{id}")
    public ResponseEntity<BusDto> updateBus(@RequestBody BusDto busDto, @PathVariable Long id) {
        return busService.updateBus(busDto, id);
    }

    @DeleteMapping("/bus/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        return busService.deleteBus(id);
    }

}
