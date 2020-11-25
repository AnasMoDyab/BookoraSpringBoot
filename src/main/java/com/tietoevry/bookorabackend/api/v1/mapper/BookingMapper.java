package com.tietoevry.bookorabackend.api.v1.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tietoevry.bookorabackend.api.v1.model.BookingDTO;
import com.tietoevry.bookorabackend.api.v1.model.BookingOfEmployeeDTO;
import com.tietoevry.bookorabackend.api.v1.model.BookingToShowDTO;
import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 *Mapper maps between Booking entity and DTOs.
 */
@Mapper
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    /**
     * Maps between Booking entity and BookingDTO.
     * @param booking  A booking object
     * @return A BookingDTO that contains date,employee Id, and zone Id
     */
    @Mapping(target = "zoneId", source = "zone.id")
    @Mapping(target = "employeeId", source = "employee.id")
    BookingDTO bookingToBookingDTO(Booking booking);

    /**
     * Maps between Booking entity and BookingToShowDTO.
     * @param booking  A booking object
     * @return A BookingToShowDTO that contains bookingId,date,zoneName, and floor
     */

    @Mapping(target = "zoneName", source = "zone.zone")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "floor", source = "zone.floor")
    BookingToShowDTO bookingToBookingToShowDto(Booking booking);
    /**
     * Maps between Booking entity and  BookingOfEmployeeDTO.
     * @param booking  A booking object
     * @return A BookingOfEmployeeDTO that contains bookingId,date,zoneName,floor, email
     */
    @Mapping(target = "zoneName", source = "zone.zone")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "floor", source = "zone.floor")
    @Mapping(target = "email", source = "employee.email")
    BookingOfEmployeeDTO bookingToBookingOfEmployeeDTO(Booking booking);

    /**
     * Maps between BookingDTO and Booking.
     * @param bookingDTO
     * @return A Booking that contains BookingId,date,employee, and zone.
     */
    Booking bookingDTOtoBooking(BookingDTO bookingDTO);
}

