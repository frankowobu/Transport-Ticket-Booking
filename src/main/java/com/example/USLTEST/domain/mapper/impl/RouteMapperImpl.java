package com.example.USLTEST.domain.mapper.impl;

import com.example.USLTEST.domain.DTO.RouteDto;
import com.example.USLTEST.domain.entity.RouteEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class RouteMapperImpl implements Mapper<RouteEntity, RouteDto> {

    private final ModelMapper modelMapper;

    @Override
    public RouteDto mapTo(RouteEntity routeEntity) {
        return modelMapper.map(routeEntity, RouteDto.class);
    }

    @Override
    public RouteEntity mapFrom(RouteDto routeDto) {
        return modelMapper.map(routeDto, RouteEntity.class);
    }

    @Override
    public Iterable<RouteDto> mapListTo(Iterable<RouteEntity> routeEntities) {
        return StreamSupport.stream(routeEntities.spliterator(), false)
                .map(routeEntity -> modelMapper.map(routeEntity, RouteDto.class))
                .collect(Collectors.toList());
    }
}
