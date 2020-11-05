package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.BookingDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.services.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Zones", description = "Zones API")
@RestController
@RequestMapping(BookingController.BASE_URL)
public class BookingController {

    public static final String BASE_URL = "/api/v1/bookings";

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO bookOneZoneOnOneDay(@RequestBody @Valid BookingDTO bookingDTO) {
        return bookingService.bookOneZoneOfOneDay(bookingDTO);
    }

}
