package com.tietoevry.bookorabackend.controllers;


import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.api.v1.model.ZoneListDTO;
import com.tietoevry.bookorabackend.services.ZoneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneListDTO getZoneList() {
        return zoneService.getAllZones();
    }

    @GetMapping({"/{id}"})
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public ZoneDTO getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

}
