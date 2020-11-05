package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneRepository extends JpaRepository<Zone,Long> {
    List<Zone> findByActivated(boolean activated);
}
