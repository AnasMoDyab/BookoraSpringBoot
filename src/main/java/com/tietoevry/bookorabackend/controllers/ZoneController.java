package com.tietoevry.bookorabackend.controllers;


import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import com.tietoevry.bookorabackend.services.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Zones", description = "Zones API")
@RestController
@RequestMapping(ZoneController.BASE_URL)
public class ZoneController {

    public static final String BASE_URL = "/api/v1/zones";

    private final ZoneRepository zoneRepository;
    private final ZoneService zoneService;

    public ZoneController(ZoneRepository zoneRepository, ZoneService zoneService) {
        this.zoneRepository = zoneRepository;
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
    public ZoneListDTO getZoneListByFloor(@PathVariable Integer floor) {
        return zoneService.getZonesByFloor(floor);
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
    public StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay(@RequestBody @Valid ZoneDateDTO zoneDateDTO) {

        return  zoneService.checkStatusOfAZoneOnADay(zoneDateDTO);

    }



    @PostMapping("/checkStatusOfAllZoneInAFloor")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor(@RequestBody @Valid FloorDateDTO floorDateDTO) {
       return zoneService.checkStatusOfAllZoneInAFloor( floorDateDTO);
    }



}
