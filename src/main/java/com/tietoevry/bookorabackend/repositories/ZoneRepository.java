package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This repository provides create, read, update and delete operations for zone.
 */
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    /**
     * Provides a list of activated zones or deactivated zones.
     *
     * @param activated True if the zone is activated
     * @return A list of activated zones or deactivated zones
     */
    List<Zone> findByActivated(boolean activated);

    /**
     * Provides a list of zones on a specific floor.
     *
     * @param floor A Integer of floor
     * @return A list of zones on a specific floor
     */
    List<Zone> findAllByFloor(Integer floor);

    /**
     * Provides a zone of a specific ID.
     *
     * @param id A id
     * @return A Zone object
     */
    Zone findZoneById(Long id);
}
