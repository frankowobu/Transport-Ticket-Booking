package com.example.USLTEST.repository;

import com.example.USLTEST.domain.entity.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {
    List<BusEntity> findByRoute_RouteId(Long routeId);
}
