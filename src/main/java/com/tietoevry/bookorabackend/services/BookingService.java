package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;

/**
 * This interface provides service for managing booking.
 */
public interface BookingService {

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
    BookingIdDTO bookOneZoneOfOneDay(BookingDTO bookingDTO) throws Exception;

    //BookingListDTO getAllBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws Exception;

    //BookingListDTO getAllValidBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws Exception;

    //BookingListDTO getAllPastBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException;

    /**
     * Provides all bookings information for a employee in a period.
     *
     * <p>The booking information includes booking ID, date of booking, floor and zone.
     *
     * @param employeeBookingInAPeriodDTO A DTO that contains email of employee, from date and to date
     * @return A DTO that contains a list of bookings of employee in that period
     * @throws EmployeeNotFoundException if employee is not found
     */
    BookingToShowDtoList getAllBookingOfEmployeeInAPeriod(EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO) throws EmployeeNotFoundException;

    /**
     * Provides all bookings information for all employees in a period.
     *
     * <p>The booking information includes booking ID, date of booking, floor, zone and employee's email.
     *
     * @param adminBookingForAllDTO A DTO that contains from date and to date
     * @return A DTO that contains a list of bookings of all employees in that period
     */
    BookingListDTOAdmin getAllBookingInAPeriodAdmin(AdminBookingForAllDTO adminBookingForAllDTO);

    /**
     * Deletes a booking of a employee.
     *
     * @param bookingId A booking ID
     * @return A message about the deleting of booking
     * @throws Exception InvalidActionException if deleting is unsuccessful
     */
    MessageDTO deleteOneBookingForEmployee(Long bookingId) throws Exception;
}
