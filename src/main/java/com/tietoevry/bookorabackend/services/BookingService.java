package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.exception.InvalidActionException;

public interface BookingService {

    BookingIdDTO bookOneZoneOfOneDay(BookingDTO bookingDTO) throws Exception;

    MessageDTO deleteOneBookingForEmployee(Long bookingId) throws InvalidActionException, Exception;

    BookingListDTO getAllBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws Exception;

    BookingListDTO getAllValidBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws Exception;

    BookingListDTO getAllPastBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException;

    BookingListDTOAdmin getAllBookingInAPeriodAdmin(AdminBookingForAllDTO adminBookingForAllDTO);


    BookingToShowDtoList getAllBookingOfEmployeeInAPeriod(EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO) throws EmployeeNotFoundException;

}
