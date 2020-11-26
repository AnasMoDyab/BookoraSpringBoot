package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.BookingMapper;
import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.BookingFailException;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.exception.InvalidActionException;
import com.tietoevry.bookorabackend.exception.ZoneNotFoundException;
import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ZoneRepository zoneRepository;
    private final EmployeeRepository employeeRepository;
    private final ZoneService zoneService;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository, ZoneRepository zoneRepository, EmployeeRepository employeeRepository, ZoneService zoneService, BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.zoneRepository = zoneRepository;
        this.employeeRepository = employeeRepository;
        this.zoneService = zoneService;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingIdDTO bookOneZoneOfOneDay(BookingDTO bookingDTO) throws Exception {

        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Employee employee = employeeRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not found"));
        Zone zone = zoneRepository.findById(bookingDTO.getZoneId())
                .orElseThrow(() -> new ZoneNotFoundException("Zone is not found"));
        LocalDate date = bookingDTO.getDate();

        //check if the zone is full on that date
        if (zoneService.isFullOnADay(zone.getId(), date)) {
            throw new BookingFailException("The zone is full");
        }
        //check if employee already have booking on that day
        else if (bookingRepository.findAllByDateAndEmployee(date, employee).size() != 0) {
            throw new BookingFailException("You already have booking on that day");
        } else {
            Booking booking = new Booking(date, employee, zone);
            Booking savedBooking = bookingRepository.save(booking);
            return new BookingIdDTO("Booking success", savedBooking.getId());
        }

    }

    @Override
    public BookingToShowDtoList getAllBookingOfEmployeeInAPeriod(EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO) throws EmployeeNotFoundException {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Employee employee = employeeRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not found"));
        List<BookingToShowDTO> bookingDTOList = new ArrayList<>();

        for (Booking booking : bookingRepository.findAllByEmployeeAndDateGreaterThanEqualAndDateLessThanEqual(employee, employeeBookingInAPeriodDTO.getFrom(), employeeBookingInAPeriodDTO.getTo())) {
            BookingToShowDTO bookingToshowDTO = bookingMapper.bookingToBookingToShowDto(booking);
            bookingDTOList.add(bookingToshowDTO);
        }

        return new BookingToShowDtoList(bookingDTOList);
    }

    @Override
    public BookingListDTOAdmin getAllBookingInAPeriodAdmin(AdminBookingForAllDTO adminBookingForAllDTO) {
        List<Booking> bookings = bookingRepository.
                findAllByDateLessThanEqualAndDateGreaterThanEqual(adminBookingForAllDTO.getTo()
                        , adminBookingForAllDTO.getFrom());


        List<BookingOfEmployeeDTO> bookingOfEmployeeDTOS = new ArrayList<>();

        for (Booking booking : bookings) {
            BookingOfEmployeeDTO bookingofEmployeeDTO = bookingMapper.bookingToBookingOfEmployeeDTO(booking);

            bookingOfEmployeeDTOS.add(bookingofEmployeeDTO);
        }


        return new BookingListDTOAdmin(bookingOfEmployeeDTOS);
    }

    @Override
    public MessageDTO deleteOneBookingForEmployee(Long bookingId) throws Exception {
        Integer bookingIdToDelete = bookingRepository.deleteBookingById(bookingId);
        if (bookingIdToDelete != 0) {
            return new MessageDTO("success deleted");
        } else {
            throw new InvalidActionException("failed deleted");
        }

    }

}
