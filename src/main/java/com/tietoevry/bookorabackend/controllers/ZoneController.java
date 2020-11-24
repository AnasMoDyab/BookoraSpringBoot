package com.tietoevry.bookorabackend.controllers;


import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.services.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A rest controller that provides API for checking information about zone.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Zones", description = "Zones API")
@RestController
@RequestMapping(ZoneController.BASE_URL)
public class ZoneController {

    public static final String BASE_URL = "/api/v1/zones";

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService) {
        this.zoneService = zoneService;
    }

/*    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneListDTO getZoneList() {
        return zoneService.getAllZones();
    }*/

    /**
     * Provides general information of all zones of the selected floor.
     *
     * <p>The information of the zone includes zone ID, floor, zone and accessibility.
     *
     * @param floor A floor
     * @return A {@code ZoneListDTO} that contains general information of all zones on that floor
     */
    @GetMapping({"/floor/{floor}"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneListDTO getZoneListByFloor(@PathVariable Integer floor) {
        ZoneListDTO zoneListDTO = zoneService.getZonesByFloor(floor);
        Comparator<ZoneDTO> compareById = (ZoneDTO zone1, ZoneDTO zone2) ->
                zone1.getId().compareTo(zone2.getId());

        Collections.sort(zoneListDTO.getZoneDTOList(), compareById);

        return zoneListDTO;
    }

/*    @GetMapping({"/zone/{id}"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneDTO getZoneById(@PathVariable Long id) throws Exception {
        return zoneService.getZoneById(id);
    }*/

/*    @PostMapping("/checkStatusOfAZoneOnADay")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay
            (@RequestBody @Valid ZoneDateDTO zoneDateDTO) throws Exception {

        return zoneService.checkStatusOfAZoneOnADay(zoneDateDTO);
    }*/

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
    @PostMapping("/checkStatusOfAllZoneInAFloor")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor
    (@RequestBody @Valid FloorDateDTO floorDateDTO) throws Exception {
        return zoneService.checkStatusOfAllZoneInAFloor(floorDateDTO);
    }

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
    @PostMapping("/ZoneSettings")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO ZoneSettings(@RequestBody @Valid ZoneSettingDTO zoneSettingDTO) throws Exception {
        return zoneService.zoneSettings(zoneSettingDTO);
    }

    /**
     * Provides total bookings of different floors.
     *
     * @param periodDTO A DTO that contains a from-date and a to-date
     * @return A list of {@code FloorStatusPeriodDTO} that shows total bookings of a floor
     */
    @PostMapping("/CheckStatusOfAllFloorPeriod")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<FloorStatusPeriodDTO> CheckStatusOfAllFloorPeriod(@RequestBody @Valid PeriodDTO periodDTO) {
        return zoneService.checkStatusOfAllFloorPeriod(periodDTO);
    }

/*    @PostMapping("/CheckStatusOfBuildingOnPeriod")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public TotalBookingInBuildingDTO CheckStatusOfBuildingOnPeriod(@RequestBody @Valid PeriodDTO periodDTO) {
        return zoneService.CheckStatusOfTheBuildingOnPeriod(periodDTO);
    }*/


}
