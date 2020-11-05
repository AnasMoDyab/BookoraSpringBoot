package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.api.v1.model.ZoneListDTO;

import java.time.LocalDate;

public interface ZoneService {

    ZoneListDTO getAllZones();

    ZoneListDTO getZonesByFloor(Integer floor);

    ZoneDTO getZoneById(Long id);

    boolean isFullOnADay(Long id, LocalDate date);

    int getTotalBookingOfADayInAZone(Long id, LocalDate date);
}
