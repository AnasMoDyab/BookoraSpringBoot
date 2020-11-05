package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.api.v1.model.ZoneListDTO;

import java.time.LocalDate;

public interface ZoneService {

    ZoneListDTO getAllZones();

    ZoneDTO getZoneById(Long id);

    boolean isFullOnADay(Long id, LocalDate date) ;
}
