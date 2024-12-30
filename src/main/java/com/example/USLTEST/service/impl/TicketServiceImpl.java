package com.example.USLTEST.service.impl;

import com.example.USLTEST.Exception.UserNotFoundException;
import com.example.USLTEST.domain.DTO.TicketDto;
import com.example.USLTEST.domain.entity.*;
import com.example.USLTEST.domain.mapper.Mapper;
import com.example.USLTEST.repository.BusRepository;
import com.example.USLTEST.repository.TicketRepository;
import com.example.USLTEST.repository.UserRepository;
import com.example.USLTEST.service.JWTService;
import com.example.USLTEST.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;

    private final BusRepository busRepository;

    private final UserRepository userRepository;

    private final JWTService jwtService;

    private final Mapper<TicketEntity, TicketDto> ticketMapper;

    @Override
    public ResponseEntity<TicketDto> createTicket(TicketDto ticketDto) {
        try{
        TicketEntity ticket = ticketMapper.mapFrom(ticketDto);
        // Retrieve Passenger ID from JWT service
        Long passengerId = jwtService.getPassengerId();

        // Find Passenger
        UserEntity passenger = userRepository.findById(passengerId)
                .orElseThrow(() -> new UserNotFoundException("Passenger not found"));

        BusEntity bus = busRepository.findById(ticketDto.getBusId())
                .orElseThrow(() -> new UserNotFoundException("Bus Operator not found"));

        // Validate seat availability
        if (bus.getCapacity() <= 0) {
            throw new IllegalStateException("No seats available.");
        }

        // Retrieve associated route from operator
        RouteEntity route = bus.getRoute();

        if (route == null) {
            throw new IllegalStateException("Route not associated with the operator.");
        }

        ticket.setUser(passenger);
        ticket.setBus(bus);
        ticket.setBookingDateTime(LocalDateTime.now());
        ticket.setStatus(Status.BOOKED);
        ticket.setPrice(route.getPrice());
        ticket.setRoute(route);
        ticket.setDepartureDay(route.getDepartureDate());
        ticket.setCancellationTime(null);
        TicketEntity savedTicket = ticketRepository.save(ticket);

        // Update operator seating and persist the change
        bus.setCapacity(bus.getCapacity() - 1);
        busRepository.save(bus);

        return new ResponseEntity<>(ticketMapper.mapTo(savedTicket), HttpStatus.CREATED);
    } catch (IllegalStateException error) {
        System.out.println(error);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

    @Override
    public ResponseEntity<TicketDto> updateTicket(TicketDto ticketDto, Long id) {
        if (ticketRepository.existsById(id)) {
            return ticketRepository.findById(id).map(existingTicket -> {
                Optional.ofNullable(ticketDto.getSeatNumber()).ifPresent(existingTicket::setSeatNumber);
                Optional.ofNullable(ticketDto.getPrice()).ifPresent(existingTicket::setPrice);
                Optional.ofNullable(ticketDto.getStatus()).ifPresent(existingTicket::setStatus);

                TicketDto savedTicketDto = ticketMapper.mapTo(ticketRepository.save(existingTicket));
                return new ResponseEntity<>(savedTicketDto, HttpStatus.OK);
            }).orElseThrow(() -> new RuntimeException("Ticket Did Not Update"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteTicket(Long id) {
        TicketEntity ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        if (ticket.getCancellationTime() != null) {
            throw new IllegalStateException("Ticket already canceled.");
        }

        if (ticketRepository.existsById(id)) {
            // Optionally, ensure the booking date is not null before clearing it
            ticket.setStatus(Status.CANCELLED);
            ticket.setCancellationTime(LocalDateTime.now());
            ticketRepository.save(ticket);

            ticketRepository.deleteById(id);

            // Update operator seating capacity
            BusEntity operator = ticket.getBus();
            operator.setCapacity(operator.getCapacity() + 1);
            busRepository.save(operator);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<TicketDto> getTicketById(Long id) {
        return ticketRepository.findById(id)
                .map(ticket -> new ResponseEntity<>(ticketMapper.mapTo(ticket), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        List<TicketDto> tickets = ticketRepository.findAll()
                .stream()
                .map(ticketMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TicketDto>> getTicketsByRouteId(Long routeId) {
        List<TicketDto> tickets = ticketRepository.findByRoute_RouteId(routeId)
                .stream()
                .map(ticketMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TicketDto>> getTicketsByBusId(Long busId) {
        List<TicketDto> tickets = ticketRepository.findByBus_BusId(busId)
                .stream()
                .map(ticketMapper::mapTo)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    public Iterable<TicketEntity> getTicketsByPassengerId() {
        Long passengerId = jwtService.getPassengerId();
        Iterable<TicketEntity> tickets = ticketRepository.findByUser_UserId(passengerId);
        return tickets;
    }
}
