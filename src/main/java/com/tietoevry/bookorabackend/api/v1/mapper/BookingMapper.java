package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.BookingDTO;
import com.tietoevry.bookorabackend.api.v1.model.BookingOfEmployeeDTO;
import com.tietoevry.bookorabackend.api.v1.model.BookingToShowDTO;
import com.tietoevry.bookorabackend.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "zoneId", source = "zone.id")
    @Mapping(target = "employeeId", source = "employee.id")
    BookingDTO bookingToBookingDTO(Booking booking);

    @Mapping(target = "zoneName", source = "zone.zone")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "floor", source = "zone.floor")
    BookingToShowDTO bookingToBookingToShowDto(Booking booking);

    @Mapping(target = "zoneName", source = "zone.zone")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "floor", source = "zone.floor")
    @Mapping(target = "email", source = "employee.email")
    BookingOfEmployeeDTO bookingToBookingOfEmployeeDTO(Booking booking);

    Booking bookingDTOtoBooking(BookingDTO bookingDTO);
}

