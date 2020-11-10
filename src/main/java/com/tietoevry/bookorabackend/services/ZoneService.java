package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public interface ZoneService {
    MessageDTO ZoneSettings(ZoneSettingDTO zoneSettingDTO);

    ZoneListDTO getAllZones();

    ZoneListDTO getZonesByFloor(Integer floor);

    ZoneDTO getZoneById(Long id);

    boolean isFullOnADay(Long id, LocalDate date);

    StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay(ZoneDateDTO zoneDateDTO);



    List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor( FloorDateDTO floorDateDTO);
    List<FloorStatusPeriodeDTO>  checkStatusOfAllFloorPeriode(PeriodeDTO periodeDTO);
    TotalBookingInBuildingDTO CheckStatusOfTheBuildingOnPeriode( PeriodeDTO floorsPeriodeDTO);
}
