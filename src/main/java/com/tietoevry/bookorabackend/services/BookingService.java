package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;

public interface BookingService {

    MessageDTO bookOneZoneOfOneDay(BookingDTO bookingDTO);

    BookingListDTO getAllBookingOfEmployee(EmployeeIdDTO employeeIdDTO);
    
    BookingListDTO getAllValidBookingOfEmployee(EmployeeIdDTO employeeIdDTO);

    BookingListDTO getAllPastBookingOfEmployee(EmployeeIdDTO employeeIdDTO);

    BookingListDTO getAllBookingOfEmployeeInAPeriod(EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO);

}
