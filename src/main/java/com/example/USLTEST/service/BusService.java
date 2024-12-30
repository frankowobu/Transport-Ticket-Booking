package com.example.USLTEST.service;

import com.example.USLTEST.domain.DTO.BusDto;
import com.example.USLTEST.domain.entity.BusEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BusService {
    ResponseEntity<BusDto> createBus(BusDto busDto);
    ResponseEntity<BusDto> updateBus(BusDto busDto, Long id);
    ResponseEntity<Void> deleteBus(Long id);
    ResponseEntity<BusDto> getBusById(Long id);
    ResponseEntity<List<BusDto>> getAllBuses();

    Iterable<BusEntity> getAllOperators();
    Iterable<BusEntity> getOperatorsByRoute(Long routeId);
    ResponseEntity<List<BusDto>> getBusesByRouteId(Long routeId);
}
