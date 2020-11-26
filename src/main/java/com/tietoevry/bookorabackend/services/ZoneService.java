package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This interface provides services for checking information about zones.
 */
public interface ZoneService {

    /**
     * Updates settings of a zone.
     *
     * <p>The API can be used to change the capacity and accessibility of a zone.
     *
     * @param zoneSettingDTO A DTO that contains information about a zone, including capacity floor, zone ID and
     *                       accessibility
     * @return A message about the result of the API call
     * @throws Exception ZoneNotFoundException if zone is not found
     */
    MessageDTO zoneSettings(ZoneSettingDTO zoneSettingDTO) throws Exception;

    //ZoneListDTO getAllZones();

    /**
     * Provides general information of all zones of the selected floor.
     *
     * <p>The information of the zone includes zone ID, floor, zone and accessibility.
     *
     * @param floor A floor
     * @return A {@code ZoneListDTO} that contains general information of all zones on that floor
     */
    ZoneListDTO getZonesByFloor(Integer floor);

    //ZoneDTO getZoneById(Long id) throws Exception;

    /**
     * Checks if the zone is full on a specific date.
     *
     * @param id   A zone id
     * @param date A date
     * @return True if the zone is full
     * @throws Exception ZoneNotFoundException if the zone is not found
     */
    boolean isFullOnADay(Long id, LocalDate date) throws Exception;

    //StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay(ZoneDateDTO zoneDateDTO) throws Exception;

    /**
     * Provides booking information of all zones of the selected floor in a selected date.
     *
     * <p>The information of the zone includes total reservation of a selected day, capacity of the zone and the booked
     * percentage.
     *
     * @param floorDateDTO A DTO that contains information about a date and a floor
     * @return A list of {@code StatusOfAZoneOnADayDTO} which contains information about the total reservation on that
     * day, capacity of the zone and the booked percentage
     * @throws Exception ZoneNotFoundException if zone is not found
     */
    List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor(FloorDateDTO floorDateDTO) throws Exception;

    /**
     * Provides total bookings of different floors.
     *
     * @param periodDTO A DTO that contains a from-date and a to-date
     * @return A list of {@code FloorStatusPeriodDTO} that shows total bookings of a floor
     */
    List<FloorStatusPeriodDTO> checkStatusOfAllFloorPeriod(PeriodDTO periodDTO);

    //TotalBookingInBuildingDTO CheckStatusOfTheBuildingOnPeriod(PeriodDTO floorsPeriodDTO);
}
