package com.example.USLTEST.domain.mapper.impl;

import com.example.USLTEST.domain.DTO.TicketList;
import com.example.USLTEST.domain.entity.TicketEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class TicketListMapperImpl implements Mapper<TicketEntity, TicketList> {

    private final ModelMapper modelMapper;

    @Override
    public TicketList mapTo(TicketEntity tickets) {
        TicketList ticketDto = new TicketList();

        ticketDto.setPassengerName(tickets.getUser().getFirstName() + " " + tickets.getUser().getLastName());
        ticketDto.setSeatNumber(tickets.getSeatNumber());
        ticketDto.setEstimatedDuration(tickets.getRoute().getDuration());
        ticketDto.setPrice(tickets.getPrice());
        ticketDto.setOrigin(tickets.getRoute().getOrigin());
        ticketDto.setDestination(tickets.getRoute().getDestination());
        ticketDto.setBookingDateTime(tickets.getBookingDateTime());
        ticketDto.setBusNumber(tickets.getBus().getBusNumber());
        ticketDto.setDriverName(tickets.getBus().getDriverName());
        ticketDto.setDepartureDate(String.valueOf(tickets.getRoute().getDepartureDate()));

        return ticketDto;
    }

    @Override
    public TicketEntity mapFrom(TicketList ticketDto) {
        return modelMapper.map(ticketDto, TicketEntity.class);
    }

    @Override
    public List<TicketList> mapListTo(Iterable<TicketEntity> ticketsIterable) {
        return StreamSupport.stream(ticketsIterable.spliterator(), false)
                .map(this::mapTo)
                .collect(Collectors.toList());
    }
}