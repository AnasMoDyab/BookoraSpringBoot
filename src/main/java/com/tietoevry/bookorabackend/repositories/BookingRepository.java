package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * This repository provides create, read, update and delete operations for booking.
 */
public interface BookingRepository extends JpaRepository<Booking, Long> {

    /**
     * Delete a booking.
     *
     * @param id A id
     * @return 1 if booking is deleted, 0 if the booking is not deleted
     */
    Integer deleteBookingById(Long id);

    /**
     * Provides a list of bookings in a specific date in a zone.
     *
     * @param date A date
     * @param zone A zone object
     * @return A list of bookings
     */
    List<Booking> findAllByDateAndZone(LocalDate date, Zone zone);

    /**
     * Provides a list of bookings in a specific date of a employee.
     *
     * @param date     A date
     * @param employee A employee object
     * @return A list of bookings
     */
    List<Booking> findAllByDateAndEmployee(LocalDate date, Employee employee);

    /**
     * Provides a list of bookings of a employee in a specific period.
     *
     * @param employee A employee object
     * @param from     A from date
     * @param to       A to date
     * @return A list of bookings
     */
    List<Booking> findAllByEmployeeAndDateGreaterThanEqualAndDateLessThanEqual(Employee employee, LocalDate from, LocalDate to);

    /**
     * Provides a list of bookings in a period.
     *
     * @param to   A to date
     * @param from A from date
     * @return A list of bookings
     */
    List<Booking> findAllByDateLessThanEqualAndDateGreaterThanEqual(LocalDate to, LocalDate from);
}
