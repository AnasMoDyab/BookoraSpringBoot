package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.BookingOfEmployeeDTO;
import com.tietoevry.bookorabackend.api.v1.model.BookingToShowDTO;
import com.tietoevry.bookorabackend.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper that maps between Booking entity and DTOs.
 */
@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    /**
     * Maps Booking entity to BookingToShowDTO.
     *
     * @param booking A Booking object
     * @return A BookingToShowDTO that contains bookingId, date, zoneName, and floor
     */

    @Mapping(target = "zoneName", source = "zone.zone")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "floor", source = "zone.floor")
    BookingToShowDTO bookingToBookingToShowDto(Booking booking);

    /**
     * Maps Booking entity to BookingOfEmployeeDTO.
     *
     * @param booking A Booking object
     * @return A BookingOfEmployeeDTO that contains bookingId, date, zoneName, floor, email of employee
     */
    @Mapping(target = "zoneName", source = "zone.zone")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "floor", source = "zone.floor")
    @Mapping(target = "email", source = "employee.email")
    BookingOfEmployeeDTO bookingToBookingOfEmployeeDTO(Booking booking);
}

