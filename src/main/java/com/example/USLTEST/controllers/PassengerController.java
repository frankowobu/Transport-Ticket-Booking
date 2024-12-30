package com.example.USLTEST.controllers;

import com.example.USLTEST.Exception.UserAlreadyExistsException;
import com.example.USLTEST.domain.DTO.*;
import com.example.USLTEST.domain.entity.BusEntity;
import com.example.USLTEST.domain.entity.TicketEntity;
import com.example.USLTEST.domain.mapper.Mapper;
import com.example.USLTEST.service.BusService;
import com.example.USLTEST.service.RouteService;
import com.example.USLTEST.service.TicketService;
import com.example.USLTEST.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/passenger")
@RequiredArgsConstructor
public class PassengerController {
    private final TicketService ticketService;

    private final RouteService routeService;

    private final BusService busService;

    private final Mapper<TicketEntity, TicketList> ticketMapper;
    private final Mapper<BusEntity, OperatorList> operatorMapper;

    private final UserService userService;

    @PostMapping("/book-ticket")
    public ResponseEntity<?> createTicket(@RequestBody TicketDto ticketDto) {
        try{
        return ticketService.createTicket(ticketDto);
    } catch (IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    }

    @PutMapping("/ticket/{id}")
    public ResponseEntity<TicketDto> updateTicket(@RequestBody TicketDto ticketDto, @PathVariable Long id) {
        return ticketService.updateTicket(ticketDto, id);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        return ticketService.deleteTicket(id);
    }

    @GetMapping("/tickets/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/all-tickets")
    public ResponseEntity<Iterable<TicketList>> getTicketsByPassengerId() {
        Iterable<TicketEntity> tickets= ticketService.getTicketsByPassengerId();
        Iterable<TicketList> allTickets = ticketMapper.mapListTo(tickets);
        return ResponseEntity.ok(allTickets);
    }

    @GetMapping("/tickets/route/{routeId}")
    public ResponseEntity<List<TicketDto>> getTicketsByRouteId(@PathVariable Long routeId) {
        return ticketService.getTicketsByRouteId(routeId);
    }

    @GetMapping("/tickets/bus/{busId}")
    public ResponseEntity<List<TicketDto>> getTicketsByBusId(@PathVariable Long busId) {
        return ticketService.getTicketsByBusId(busId);
    }
    @GetMapping("/buses/{id}")
    public ResponseEntity<BusDto> getBusById(@PathVariable Long id) {
        return busService.getBusById(id);
    }

    @GetMapping("/all-buses")
    public ResponseEntity<List<BusDto>> getAllBuses() {
        return busService.getAllBuses();
    }

    @GetMapping("/get-operators")
    public ResponseEntity<Iterable<OperatorList>> getAllOperators() {
        Iterable<BusEntity> operators = busService.getAllOperators();
        Iterable<OperatorList> allOperators = operatorMapper.mapListTo(operators);
        return ResponseEntity.ok(allOperators);
    }
    @GetMapping("/get-operators-route")
    public ResponseEntity<Iterable<OperatorList>> getAllOperatorsByRoute(Long routeId) {
        Iterable<BusEntity> operators = busService.getOperatorsByRoute(routeId);
        Iterable<OperatorList> allOperatorsByRoute = operatorMapper.mapListTo(operators);
        return ResponseEntity.ok(allOperatorsByRoute);
    }
    @GetMapping("/all-buses-route/routeId")
    public ResponseEntity<List<BusDto>> getAllBusesByRoute(Long routeId) {
        return busService.getBusesByRouteId(routeId);
    }

    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        return routeService.deleteRoute(id);
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable Long id) {
        return routeService.getRouteById(id);
    }

    @GetMapping("/all-routes")
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        return routeService.getAllRoutes();
    }

//    @GetMapping("/bus/{routeId}")
//    public ResponseEntity<List<BusDto>> getRoutesByBusId(@PathVariable Long busId) {
//       return routeService.getRoutesByBusId(busId);
//    }

    @GetMapping("/routes/search")
    public ResponseEntity<List<RouteDto>> findRoutes(@RequestParam String origin, @RequestParam String destination) {
        return routeService.findRoutes(origin, destination);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        return userService.updateUser(userDto, id);
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        return userService.changePassword(request, id);
    }
}
