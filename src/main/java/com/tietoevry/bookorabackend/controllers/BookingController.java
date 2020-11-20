package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.services.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Booking", description = "Booking API")
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
    public BookingIdDTO bookOneZoneOnOneDay(@RequestBody @Valid BookingDTO bookingDTO) throws Exception {
        return bookingService.bookOneZoneOfOneDay(bookingDTO);
    }

    @PostMapping("/getAllBookingOfEmployee")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookingListDTO getAllBookingOfEmployee(@RequestBody @Valid EmployeeEmailDTO employeeEmailDTO) throws Exception {
        return bookingService.getAllBookingOfEmployee(employeeEmailDTO);
    }

    @PostMapping("/getAllValidBookingOfEmployee")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookingListDTO getAllValidBookingOfEmployee(@RequestBody @Valid EmployeeEmailDTO employeeEmailDTO) throws Exception {
        return bookingService.getAllValidBookingOfEmployee(employeeEmailDTO);
    }

    @PostMapping("/getAllPastBookingOfEmployee")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookingListDTO getAllPastBookingOfEmployee(@RequestBody @Valid EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException {
        return bookingService.getAllPastBookingOfEmployee(employeeEmailDTO);
    }

    @PostMapping("/getAllBookingOfEmployeeInAPeriod")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public BookingToshowDtoList getAllBookingOfEmployeeInAPeriod(@RequestBody @Valid EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO) throws EmployeeNotFoundException {
        return bookingService.getAllBookingOfEmployeeInAPeriod(employeeBookingInAPeriodDTO);
    }

    @PostMapping("/getAllBookingOfEmployeesInAPeriodAdmin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookingListDTOAdmin getAllBookingInAPeriodAdmin(@RequestBody @Valid AdminBookingForAllDTO adminBookingForAllDTO) {
        return bookingService.getAllBookingInAPeriodAdmin(adminBookingForAllDTO);
    }


    @PostMapping("/deleteBooking")
    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO deleteBooking(@RequestBody @Valid DeleteBookingByIdDTO deleteBookingByIdDTO) throws Exception {
        return bookingService.deleteOneBookingForEmployee(deleteBookingByIdDTO.getBookingId());
    }

}
