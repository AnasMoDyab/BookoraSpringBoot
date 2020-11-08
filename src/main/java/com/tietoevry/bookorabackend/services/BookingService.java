package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;

public interface BookingService {

    MessageDTO bookOneZoneOfOneDay(BookingDTO bookingDTO);

    MessageDTO deleteOneBookingForEmployee(BookingDTO bookingDTO);

    BookingListDTO getAllBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO);

    BookingListDTO getAllValidBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO);

    BookingListDTO getAllPastBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO);


    BookingToshowDtoList  getAllBookingOfEmployeeInAPeriod(EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO);

}
