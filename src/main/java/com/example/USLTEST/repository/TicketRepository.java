package com.example.USLTEST.repository;

import com.example.USLTEST.domain.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    Iterable<TicketEntity> findByUser_UserId(Long userId);
    List<TicketEntity> findByRoute_RouteId(Long routeId);
    List<TicketEntity> findByBus_BusId(Long busId);
}
