package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findByActivated(boolean activated);

    List<Zone> findAllByFloor(Integer floor);

    Zone findZoneById(Long id);

    @Query("SELECT SUM(m.capacity) FROM Zone m")
    Integer selectTotalsCapacities();
}
