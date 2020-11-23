package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;

import java.time.LocalDate;
import java.util.List;

public interface ZoneService {
    MessageDTO zoneSettings(ZoneSettingDTO zoneSettingDTO) throws Exception;

    ZoneListDTO getAllZones();

    ZoneListDTO getZonesByFloor(Integer floor);

    ZoneDTO getZoneById(Long id) throws Exception;

    boolean isFullOnADay(Long id, LocalDate date) throws Exception;

    StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay(ZoneDateDTO zoneDateDTO) throws Exception;


    List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor(FloorDateDTO floorDateDTO) throws Exception;

    List<FloorStatusPeriodDTO> checkStatusOfAllFloorPeriod(PeriodDTO periodDTO);

    TotalBookingInBuildingDTO CheckStatusOfTheBuildingOnPeriod(PeriodDTO floorsPeriodDTO);
}
