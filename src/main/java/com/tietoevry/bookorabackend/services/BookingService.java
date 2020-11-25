package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;

public interface BookingService {

    BookingIdDTO bookOneZoneOfOneDay(BookingDTO bookingDTO) throws Exception;

    //BookingListDTO getAllBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws Exception;

    //BookingListDTO getAllValidBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws Exception;

    //BookingListDTO getAllPastBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException;

    BookingToShowDtoList getAllBookingOfEmployeeInAPeriod(EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO) throws EmployeeNotFoundException;

    BookingListDTOAdmin getAllBookingInAPeriodAdmin(AdminBookingForAllDTO adminBookingForAllDTO);

    MessageDTO deleteOneBookingForEmployee(Long bookingId) throws Exception;
}
