package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.ZoneNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ZoneService {
    MessageDTO zoneSettings(ZoneSettingDTO zoneSettingDTO);

    ZoneListDTO getAllZones();

    ZoneListDTO getZonesByFloor(Integer floor);

    ZoneDTO getZoneById(Long id) throws Exception;

    boolean isFullOnADay(Long id, LocalDate date) throws ZoneNotFoundException, Exception;

    StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay(ZoneDateDTO zoneDateDTO) throws Exception;



    List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor( FloorDateDTO floorDateDTO) throws Exception;
    List<FloorStatusPeriodDTO> checkStatusOfAllFloorPeriod(PeriodeDTO periodeDTO);
    TotalBookingInBuildingDTO CheckStatusOfTheBuildingOnPeriod(PeriodeDTO floorsPeriodDTO);
}
