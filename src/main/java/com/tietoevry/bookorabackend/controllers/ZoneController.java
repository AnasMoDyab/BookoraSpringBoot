package com.tietoevry.bookorabackend.controllers;


import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.services.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Zones", description = "Zones API")
@RestController
@RequestMapping(ZoneController.BASE_URL)
public class ZoneController {

    public static final String BASE_URL = "/api/v1/zones";

    private final ZoneService zoneService;

    public ZoneController(ZoneService zoneService)
    {
        this.zoneService = zoneService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneListDTO getZoneList() {
        return zoneService.getAllZones();
    }

    @GetMapping({"/floor/{floor}"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneListDTO getZoneListByFloor(@PathVariable Integer floor)
    {
        ZoneListDTO zoneListDTO =zoneService.getZonesByFloor(floor);
        Comparator<ZoneDTO> compareById = (ZoneDTO zone1, ZoneDTO zone2) ->
                zone1.getId().compareTo( zone2.getId() );

        Collections.sort(zoneListDTO.getZoneDTOList(), compareById);


        return zoneListDTO;
    }

    @GetMapping({"/zone/{id}"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneDTO getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @PostMapping("/checkStatusOfAZoneOnADay")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay
            (@RequestBody @Valid ZoneDateDTO zoneDateDTO) {

        return  zoneService.checkStatusOfAZoneOnADay(zoneDateDTO);
    }

    @PostMapping("/checkStatusOfAllZoneInAFloor")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor
            (@RequestBody @Valid FloorDateDTO floorDateDTO) {
        return zoneService.checkStatusOfAllZoneInAFloor( floorDateDTO);
    }

    @PostMapping("/ZoneSettings")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO ZoneSetings(@RequestBody @Valid ZoneSettingDTO zoneSettingDTO) {
        return zoneService.ZoneSettings(zoneSettingDTO);
    }

    @PostMapping("/CheckStatusOfAllFloorPeriode")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<FloorStatusPeriodeDTO> CheckStatusOfAllFloorPeriode(@RequestBody @Valid PeriodeDTO periodeDTO) {
        return zoneService.checkStatusOfAllFloorPeriode(periodeDTO);
    }

    @PostMapping("/CheckStatusOfBuildingOnPeriode")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public TotalBookingInBuildingDTO CheckStatusOfBuildingOnPeriode(@RequestBody @Valid PeriodeDTO periodeDTO) {
        return zoneService.CheckStatusOfTheBuildingOnPeriode(periodeDTO);
    }



}
