package com.example.USLTEST.domain.mapper.impl;

import com.example.USLTEST.domain.DTO.TicketDto;
import com.example.USLTEST.domain.entity.TicketEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class TicketMapperImpl implements Mapper<TicketEntity, TicketDto> {

    private final ModelMapper modelMapper;

    @Override
    public TicketDto mapTo(TicketEntity ticketEntity) {
        return modelMapper.map(ticketEntity, TicketDto.class);
    }

    @Override
    public TicketEntity mapFrom(TicketDto ticketDto) {
        return modelMapper.map(ticketDto, TicketEntity.class);
    }

    @Override
    public Iterable<TicketDto> mapListTo(Iterable<TicketEntity> ticketEntities) {
        return StreamSupport.stream(ticketEntities.spliterator(), false)
                .map(ticketEntity -> modelMapper.map(ticketEntity, TicketDto.class))
                .collect(Collectors.toList());
    }
}
