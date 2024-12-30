package com.example.USLTEST.service;

import com.example.USLTEST.domain.DTO.TicketDto;
import com.example.USLTEST.domain.entity.TicketEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TicketService {
    ResponseEntity<TicketDto> createTicket(TicketDto ticketDto);
    ResponseEntity<TicketDto> updateTicket(TicketDto ticketDto, Long id);
    ResponseEntity<Void> deleteTicket(Long id);
    ResponseEntity<TicketDto> getTicketById(Long id);
    ResponseEntity<List<TicketDto>> getAllTickets();
    Iterable<TicketEntity> getTicketsByPassengerId();
    ResponseEntity<List<TicketDto>> getTicketsByRouteId(Long routeId);
    ResponseEntity<List<TicketDto>> getTicketsByBusId(Long busId);
}
