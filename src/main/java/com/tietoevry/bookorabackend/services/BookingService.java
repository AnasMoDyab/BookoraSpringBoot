package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;

public interface BookingService {

    BookingIdDTO bookOneZoneOfOneDay(BookingDTO bookingDTO) throws Exception;

    MessageDTO deleteOneBookingForEmployee(Long bookingId);

    BookingListDTO getAllBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO);

    BookingListDTO getAllValidBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO);

    BookingListDTO getAllPastBookingOfEmployee(EmployeeEmailDTO employeeEmailDTO);

    BookingListDTOAdmin  getAllBookingInAPeriodAdmin(AdminBookingForAllDTO adminBookingForAllDTO);


    BookingToshowDtoList  getAllBookingOfEmployeeInAPeriod(EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO);

}
