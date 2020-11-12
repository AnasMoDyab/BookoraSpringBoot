package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.BookingMapper;
import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


//NB! Surefire pick up tests with name Test
//NB! Failsafe pick up test with name IT

@DisplayName("BookingServiceImp unit test")
@Tag("Development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class BookingServiceImplUnitTest {

    @Mock
    BookingRepository bookingRepository;
    @Mock
    ZoneRepository zoneRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    ZoneService zoneService;
    @Mock
    BookingMapper bookingMapper;


    @InjectMocks
    BookingServiceImpl bookingService;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("Delete booking with correct Id")
    @Test
    void deleteOneBookingForEmployeeWithCorrectIdTest() {
        //Given with correct id
        given(bookingRepository.deleteBookingById(1L)).willReturn(1);

        //when
        MessageDTO messageDTO = bookingService.deleteOneBookingForEmployee(1L);

        //then
        assertThat(messageDTO.getMessage()).isEqualTo("success deleted");
        then(bookingRepository).should(times(1)).deleteBookingById(anyLong());
    }

    @DisplayName("Delete booking with wrong Id")
    @Test
    void deleteOneBookingForEmployeeWithWrongIdTest() {
        //Given with wrong id
        given(bookingRepository.deleteBookingById(2L)).willReturn(0);

        //when
        MessageDTO messageDTO = bookingService.deleteOneBookingForEmployee(2L);

        //then
        assertThat(messageDTO.getMessage()).isEqualTo("failed deleted");
        then(bookingRepository).should(times(1)).deleteBookingById(anyLong());
    }

    @DisplayName("Get all booking of employee")
    @Test
    void getAllBookingOfEmployee() {
        //Given
        Employee employee = new Employee();
        EmployeeEmailDTO employeeEmailDTO = new EmployeeEmailDTO("root@tietoevry.com");
        EmployeeEmailDTO employeeEmailDTO2 = new EmployeeEmailDTO("root2@tietoevry.com");
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        given(employeeRepository.findByEmail("root@tietoevry.com")).willReturn(Optional.of(employee));
        given(bookingRepository.findAllByEmployee(employee)).willReturn(bookings);

        //when
        BookingListDTO bookingListDTO = bookingService.getAllBookingOfEmployee(employeeEmailDTO);

        //then
        assertThat(bookingListDTO.getBookingDTOList().size()).isEqualTo(1);
        assertThatThrownBy(() -> {
            bookingService.getAllBookingOfEmployee(employeeEmailDTO2);
        }).isInstanceOf(RuntimeException.class);
        then(employeeRepository).should(times(2)).findByEmail(anyString());
        then(bookingRepository).should(times(1)).findAllByEmployee(any(Employee.class));
    }

    @DisplayName("Get all valid booking of employee")
    @Tag("UnitTest")
    @Test
    void getAllValidBookingOfEmployee() {
        //Given
        Employee employee = new Employee();
        EmployeeEmailDTO employeeEmailDTO = new EmployeeEmailDTO("root@tietoevry.com");
        EmployeeEmailDTO employeeEmailDTO2 = new EmployeeEmailDTO("root2@tietoevry.com");
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        given(employeeRepository.findByEmail("root@tietoevry.com")).willReturn(Optional.of(employee));
        given(bookingRepository.findAllByEmployeeAndDateGreaterThanEqual(employee, LocalDate.now())).willReturn(bookings);

        //when
        BookingListDTO bookingListDTO = bookingService.getAllValidBookingOfEmployee(employeeEmailDTO);

        //then
        assertThat(bookingListDTO.getBookingDTOList().size()).isEqualTo(1);
        assertThatThrownBy(() -> {
            bookingService.getAllValidBookingOfEmployee(employeeEmailDTO2);
        }).isInstanceOf(RuntimeException.class);
        then(employeeRepository).should(times(2)).findByEmail(anyString());
        then(bookingRepository).should(times(1)).findAllByEmployeeAndDateGreaterThanEqual(any(Employee.class), any(LocalDate.class));
    }

    @DisplayName("Get all past booking of employee")
    @Test
    void getAllPastBookingOfEmployee() {
        //Given
        Employee employee = new Employee();
        EmployeeEmailDTO employeeEmailDTO = new EmployeeEmailDTO("root@tietoevry.com");
        EmployeeEmailDTO employeeEmailDTO2 = new EmployeeEmailDTO("root2@tietoevry.com");
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        given(employeeRepository.findByEmail("root@tietoevry.com")).willReturn(Optional.of(employee));
        given(bookingRepository.findAllByEmployeeAndDateBefore(employee, LocalDate.now())).willReturn(bookings);

        //when
        BookingListDTO bookingListDTO = bookingService.getAllPastBookingOfEmployee(employeeEmailDTO);

        //then
        assertThat(bookingListDTO.getBookingDTOList().size()).isEqualTo(1);
        assertThatThrownBy(() -> {
            bookingService.getAllPastBookingOfEmployee(employeeEmailDTO2);
        }).isInstanceOf(RuntimeException.class);
        then(employeeRepository).should(times(2)).findByEmail(anyString());
        then(bookingRepository).should(times(1)).findAllByEmployeeAndDateBefore(any(Employee.class), any(LocalDate.class));
    }

    @DisplayName("Get all past booking of employee in a period for admin")
    @Test
    void getAllBookingInAPeriodAdmin() {
        //Given
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        LocalDate date = LocalDate.now();
        AdminBookingForAllDTO adminBookingForAllDTO = new AdminBookingForAllDTO(date, date);
        given(bookingRepository.findAllByDateLessThanEqualAndDateGreaterThanEqual(date, date)).willReturn(bookings);

        //when
        BookingListDTOAdmin bookingListDTOAdmin = bookingService.getAllBookingInAPeriodAdmin(adminBookingForAllDTO);

        //then
        assertThat(bookingListDTOAdmin.getBookingofEmployeeDTOList().size()).isEqualTo(1);
        then(bookingRepository).should(times(1)).findAllByDateLessThanEqualAndDateGreaterThanEqual(any(LocalDate.class), any(LocalDate.class));

    }

    @DisplayName("Get all booking of employee in a period")
    @Test
    void getAllBookingOfEmployeeInAPeriod() {
        //Given
        Employee employee = new Employee();
        EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO = new EmployeeBookingInAPeriodDTO("root@tietoevry.com", LocalDate.now(), LocalDate.now());
        EmployeeBookingInAPeriodDTO employeeBookingInAPeriodDTO2 = new EmployeeBookingInAPeriodDTO("root2@tietoevry.com", LocalDate.now(), LocalDate.now());
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        given(employeeRepository.findByEmail("root@tietoevry.com")).willReturn(Optional.of(employee));
        given(bookingRepository.findAllByEmployeeAndDateGreaterThanEqualAndDateLessThanEqual(employee, LocalDate.now(), LocalDate.now())).willReturn(bookings);

        //when
        BookingToshowDtoList bookingToshowDtoList = bookingService.getAllBookingOfEmployeeInAPeriod(employeeBookingInAPeriodDTO);

        //then
        assertThat(bookingToshowDtoList.getBookingToshowDtoLists().size()).isEqualTo(1);
        assertThatThrownBy(() -> {
            bookingService.getAllBookingOfEmployeeInAPeriod(employeeBookingInAPeriodDTO2);
        }).isInstanceOf(RuntimeException.class);
        then(employeeRepository).should(times(2)).findByEmail(anyString());
        then(bookingRepository).should(times(1)).findAllByEmployeeAndDateGreaterThanEqualAndDateLessThanEqual(any(Employee.class), any(LocalDate.class), any(LocalDate.class));
    }

    @DisplayName("Book a zone in a day")
    @Nested
    class BookingTest {

        LocalDate date;
        BookingDTO bookingDTO;
        Employee employee;
        Zone zone;
        List<Booking> bookings;
        Booking booking;

        @BeforeEach
        void setUp() {
            date = LocalDate.of(2020, 11, 11);
            bookingDTO = new BookingDTO(date, 1L, 1L);
            employee = new Employee();
            Zone zone = new Zone();
            zone.setId(1L);
            booking = new Booking();
            bookings = new ArrayList<>();
            given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));
            given(zoneRepository.findById(anyLong())).willReturn(Optional.of(zone));
        }

        @DisplayName("Book a zone which is full")
        @Test
        void bookOneZoneOfOneDayWhichIsFullTest() {
            //Given
            given(zoneService.isFullOnADay(1L, date)).willReturn(true);

            //when
            BookingIdDTO returnedBookingIdDTO = bookingService.bookOneZoneOfOneDay(bookingDTO);

            //then
            assertThat(returnedBookingIdDTO.getMessage()).isEqualTo("The zone is full");
            assertThat(returnedBookingIdDTO.getBookingId()).isNull();
            then(employeeRepository).should(times(1)).findById(anyLong());
            then(zoneRepository).should(times(1)).findById(anyLong());
            then(zoneService).should(times(1)).isFullOnADay(anyLong(), any(LocalDate.class));
        }

        @DisplayName("Book a zone in a day which the employee already have booking")
        @Test
        void bookOneZoneOfOneDayWhichAlreadyHaveBookingTest() {
            //Given
            bookings.add(booking);
            given(zoneService.isFullOnADay(1L, date)).willReturn(false);
            given(bookingRepository.findAllByDateAndEmployee(any(LocalDate.class), any(Employee.class))).willReturn(bookings);

            //when
            BookingIdDTO returnedBookingIdDTO = bookingService.bookOneZoneOfOneDay(bookingDTO);

            //then
            assertThat(returnedBookingIdDTO.getMessage()).isEqualTo("You already have booking on that day");
            assertThat(returnedBookingIdDTO.getBookingId()).isNull();
            then(employeeRepository).should(times(1)).findById(anyLong());
            then(zoneRepository).should(times(1)).findById(anyLong());
            then(zoneService).should(times(1)).isFullOnADay(anyLong(), any(LocalDate.class));
            then(bookingRepository).should(times(1)).findAllByDateAndEmployee(any(LocalDate.class), any(Employee.class));
        }

        @DisplayName("Book a zone in a day successfully")
        @Test
        void bookOneZoneOfOneDaySuccessfullyTest() {
            //Given
            booking.setDate(date);
            Booking savedBooking = new Booking();
            savedBooking.setId(1L);
            given(zoneService.isFullOnADay(1L, date)).willReturn(false);
            given(bookingRepository.findAllByDateAndEmployee(any(LocalDate.class), any(Employee.class))).willReturn(bookings);
            given(bookingRepository.save(booking)).willReturn(savedBooking);

            //when
            BookingIdDTO returnedBookingIdDTO = bookingService.bookOneZoneOfOneDay(bookingDTO);

            //then
            assertThat(returnedBookingIdDTO.getMessage()).isEqualTo("Booking success");
            assertThat(returnedBookingIdDTO.getBookingId()).isEqualTo(1L);
            then(employeeRepository).should(times(1)).findById(anyLong());
            then(zoneRepository).should(times(1)).findById(anyLong());
            then(zoneService).should(times(1)).isFullOnADay(anyLong(), any(LocalDate.class));
            then(bookingRepository).should(times(1)).findAllByDateAndEmployee(any(LocalDate.class), any(Employee.class));
            then(bookingRepository).should(times(1)).save(any(Booking.class));
        }
    }
}