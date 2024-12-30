package com.example.USLTEST.service.impl;

import com.example.USLTEST.domain.DTO.RouteDto;
import com.example.USLTEST.domain.entity.RouteEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import com.example.USLTEST.repository.RouteRepository;
import com.example.USLTEST.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final Mapper<RouteEntity, RouteDto> routeMapper;

    @Override
    public ResponseEntity<RouteDto> createRoute(RouteDto routeDto) {
        RouteEntity route = routeMapper.mapFrom(routeDto);
        RouteEntity savedRoute = routeRepository.save(route);
        return new ResponseEntity<>(routeMapper.mapTo(savedRoute), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<RouteDto> updateRoute(RouteDto routeDto, Long id) {
        if (routeRepository.existsById(id)) {
            return routeRepository.findById(id).map(existingRoute -> {
                Optional.ofNullable(routeDto.getOrigin()).ifPresent(existingRoute::setOrigin);
                Optional.ofNullable(routeDto.getDestination()).ifPresent(existingRoute::setDestination);
                Optional.ofNullable(routeDto.getDuration()).ifPresent(existingRoute::setDuration);
                Optional.ofNullable(routeDto.getPrice()).ifPresent(existingRoute::setPrice);
                Optional.ofNullable(routeDto.getDepartureDate()).ifPresent(existingRoute::setDepartureDate);
                Optional.ofNullable(routeDto.getDepartureTime()).ifPresent(existingRoute::setDepartureTime);

                RouteDto savedRouteDto = routeMapper.mapTo(routeRepository.save(existingRoute));
                return new ResponseEntity<>(savedRouteDto, HttpStatus.OK);
            }).orElseThrow(() -> new RuntimeException("Route Did Not Update"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteRoute(Long id) {
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<RouteDto> getRouteById(Long id) {
        return routeRepository.findById(id)
                .map(route -> new ResponseEntity<>(routeMapper.mapTo(route), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        List<RouteDto> routes = routeRepository.findAll()
                .stream()
                .map(routeMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<List<RouteDto>> getRoutesByBusId(Long busId) {
//        List<RouteDto> routes = routeRepository.findByBus_BusId(busId)
//                .stream()
//                .map(routeMapper::mapTo)
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(routes, HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<List<RouteDto>> findRoutes(String startLocation, String endLocation) {
        List<RouteDto> routes = routeRepository.findByOriginAndDestination(startLocation, endLocation)
                .stream()
                .map(routeMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }
}
