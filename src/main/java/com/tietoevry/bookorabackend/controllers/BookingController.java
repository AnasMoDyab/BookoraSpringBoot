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

/**
 * A rest controller that provides API for managing booking.
 */
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

    /**
     * Books a zone on a day by a employee.
     *
     * @param bookingDTO A DTO that contains date of booking, employee ID and zone ID
     * @return A DTO that contains a message about the booking and the booking ID
     * @throws Exception ZoneNotFoundException if zone is not found
     * @throws Exception EmployeeNotFoundException if employee is not found
     * @throws Exception BookingFailException if the zone is full
     * @throws Exception BookingFailException if the employee already have booking on that day
     */
    @PostMapping("/book")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingIdDTO bookOneZoneOnOneDay(@RequestBody @Valid BookingDTO bookingDTO) throws Exception {
        return bookingService.bookOneZoneOfOneDay(bookingDTO);
    }

/*    @PostMapping("/getAllBookingOfEmployee")
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
    }*/

    /**
     * Provides all bookings information for a employee in a period.
     *
     * <p>The booking information includes booking ID, date of booking, floor and zone.
     *
     * @param employeeBookingInAPeriodDTO A DTO that contains email of employee, from date and to date
     * @return A DTO that contains a list of bookings of employee in that period
     * @throws EmployeeNotFoundException if employee is not found
     */
    @PostMapping("/getAllBookingOfEmployeeInAPeriod")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public BookingToShowDtoList getAllBookingOfEmployeeInAPeriod(@RequestBody @Valid EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO) throws EmployeeNotFoundException {
        return bookingService.getAllBookingOfEmployeeInAPeriod(employeeBookingInAPeriodDTO);
    }

    /**
     * Provides all bookings information for all employees in a period.
     *
     * <p>The booking information includes booking ID, date of booking, floor, zone and employee's email.
     *
     * @param adminBookingForAllDTO A DTO that contains from date and to date
     * @return A DTO that contains a list of bookings of all employees in that period
     */
    @PostMapping("/getAllBookingOfEmployeesInAPeriodAdmin")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public BookingListDTOAdmin getAllBookingInAPeriodAdmin(@RequestBody @Valid AdminBookingForAllDTO adminBookingForAllDTO) {
        return bookingService.getAllBookingInAPeriodAdmin(adminBookingForAllDTO);
    }


    /**
     * Deletes a booking of a employee.
     *
     * @param deleteBookingByIdDTO A booking ID
     * @return A message about the deleting of booking
     * @throws Exception InvalidActionException if deleting is unsuccessful
     */
    @PostMapping("/deleteBooking")
    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO deleteBooking(@RequestBody @Valid DeleteBookingByIdDTO deleteBookingByIdDTO) throws Exception {
        return bookingService.deleteOneBookingForEmployee(deleteBookingByIdDTO.getBookingId());
    }

}
