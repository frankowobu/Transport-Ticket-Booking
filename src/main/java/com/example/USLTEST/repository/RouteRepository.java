package com.example.USLTEST.repository;

import com.example.USLTEST.domain.entity.RouteEntity;
import com.example.USLTEST.domain.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, Long> {
    List<RouteEntity> findByOriginAndDestination(String origin, String destination);
}
