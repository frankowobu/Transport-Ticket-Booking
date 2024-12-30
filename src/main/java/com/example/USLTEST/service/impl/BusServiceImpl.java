package com.example.USLTEST.service.impl;

import com.example.USLTEST.domain.DTO.BusDto;
import com.example.USLTEST.domain.entity.BusEntity;
import com.example.USLTEST.domain.entity.RouteEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import com.example.USLTEST.repository.BusRepository;
import com.example.USLTEST.repository.RouteRepository;
import com.example.USLTEST.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final Mapper<BusEntity, BusDto> busMapper;

    @Override
    public ResponseEntity<BusDto> createBus(BusDto busDto) {
        Optional<RouteEntity> routeOpt = routeRepository.findById(busDto.getRouteId());
        if (routeOpt.isPresent()) {
            BusEntity bus = busMapper.mapFrom(busDto);
            bus.setRoute(routeOpt.get());
            BusEntity savedBus = busRepository.save(bus);
            return new ResponseEntity<>(busMapper.mapTo(savedBus), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<BusDto> updateBus(BusDto busDto, Long id) {
        if (busRepository.existsById(id)) {
            return busRepository.findById(id).map(existingBus -> {
                Optional.ofNullable(busDto.getBusNumber()).ifPresent(existingBus::setBusNumber);
                Optional.ofNullable(busDto.getCapacity()).ifPresent(existingBus::setCapacity);
                if (busDto.getRouteId() != null) {
                    Optional<RouteEntity> routeOpt = routeRepository.findById(busDto.getRouteId());
                    routeOpt.ifPresent(existingBus::setRoute);
                }

                BusDto savedBusDto = busMapper.mapTo(busRepository.save(existingBus));
                return new ResponseEntity<>(savedBusDto, HttpStatus.OK);
            }).orElseThrow(() -> new RuntimeException("Bus Did Not Update"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteBus(Long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<BusDto> getBusById(Long id) {
        return busRepository.findById(id)
                .map(bus -> new ResponseEntity<>(busMapper.mapTo(bus), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<BusDto>> getAllBuses() {
        List<BusDto> buses = busRepository.findAll()
                .stream()
                .map(busMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }
    public Iterable<BusEntity> getAllOperators() {
        Iterable<BusEntity> operators = busRepository.findAll();
        return operators;
    }

    public Iterable<BusEntity> getOperatorsByRoute(Long routeId) {
        Iterable<BusEntity> operators = busRepository.findByRoute_RouteId(routeId);
        return operators;
    }
    @Override
    public ResponseEntity<List<BusDto>> getBusesByRouteId(Long routeId) {
        List<BusDto> buses = busRepository.findAll()
                .stream()
                .filter(bus -> bus.getRoute() != null && bus.getRoute().getRouteId().equals(routeId))
                .map(busMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(buses, HttpStatus.OK);
    }
}
