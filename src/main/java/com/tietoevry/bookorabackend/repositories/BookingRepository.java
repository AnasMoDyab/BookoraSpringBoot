package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findAllByDateAndZone(LocalDate data, Zone zone);
    List<Booking> findAllByDateAndEmployee(LocalDate date, Employee employee);
    List<Booking> findAllByEmployee(Employee employee);
    List<Booking> findAllByZone(Zone zone);
    List<Booking> findAllByDate(LocalDate date);
}
