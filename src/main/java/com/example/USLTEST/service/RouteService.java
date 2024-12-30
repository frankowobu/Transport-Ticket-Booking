package com.example.USLTEST.service;

import com.example.USLTEST.domain.DTO.RouteDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RouteService {
    ResponseEntity<RouteDto> createRoute(RouteDto routeDto);
    ResponseEntity<RouteDto> updateRoute(RouteDto routeDto, Long id);
    ResponseEntity<Void> deleteRoute(Long id);
    ResponseEntity<RouteDto> getRouteById(Long id);
    ResponseEntity<List<RouteDto>> getAllRoutes();
//    ResponseEntity<List<RouteDto>> getRoutesByBusId(Long busId);
    ResponseEntity<List<RouteDto>> findRoutes(String startLocation, String endLocation);
}
