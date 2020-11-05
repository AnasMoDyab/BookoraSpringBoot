package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.BookingDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ZoneRepository zoneRepository;
    private final EmployeeRepository employeeRepository;
    private final ZoneService zoneService;

    public BookingServiceImpl(BookingRepository bookingRepository, ZoneRepository zoneRepository, EmployeeRepository employeeRepository, ZoneService zoneService) {
        this.bookingRepository = bookingRepository;
        this.zoneRepository = zoneRepository;
        this.employeeRepository = employeeRepository;
        this.zoneService = zoneService;
    }

    @Override
    public MessageDTO bookOneZoneOfOneDay(BookingDTO bookingDTO) {

        Employee employee = employeeRepository.findById(bookingDTO.getEmployeeId()).orElseThrow(RuntimeException::new);
        Zone zone = zoneRepository.findById(bookingDTO.getZoneId()).orElseThrow(RuntimeException::new);
        LocalDate date = bookingDTO.getDate();

        //check if the zone is full on that date
        if(zoneService.isFullOnADay(zone.getId(), date)){
            return new MessageDTO("The zone is full");
        }
        //check if employee already have booking on that day
        else if (bookingRepository.findAllByDateAndEmployee(date,employee).size() != 0){
            return new MessageDTO("You already have booking on that day");
        }
        else{
            Booking booking = new Booking(date, employee, zone);
            bookingRepository.save(booking);
            return new MessageDTO("Booking success");
        }

    }
}
