package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.ZoneMapper;
import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.InvalidActionException;
import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

@DisplayName("ZoneServiceImp unit test")
@Tag("Development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class ZoneServiceImplTest {

    @Mock
    ZoneRepository zoneRepository;
    @Mock
    ZoneMapper zoneMapper;
    @Mock
    BookingRepository bookingRepository;

    @InjectMocks
    ZoneServiceImpl zoneService;

    @AfterEach
    void tearDown() {
        reset(zoneRepository);
        reset(zoneMapper);
        reset(bookingRepository);
    }

    @DisplayName("Change a zone with valid zone ID")
    @Test
    void zoneSettingsWithValidZoneId() throws Exception {
        //given
        Zone zoneToChange = new Zone(1L, 1, 'A', false, 10, new HashSet<>());
        ZoneSettingDTO zoneSettingDTO = new ZoneSettingDTO(2, 1L, 20, true);
        given(zoneRepository.findZoneById(any())).willReturn(zoneToChange);

        //when
        MessageDTO messageDTO = zoneService.zoneSettings(zoneSettingDTO);

        //then
        assertThat(messageDTO.getMessage()).isEqualTo("Modified successfully");
        assertThat(zoneToChange.isActivated()).isEqualTo(true);
        assertThat(zoneToChange.getCapacity()).isEqualTo(20);
        assertThat(zoneToChange.getFloor()).isEqualTo(2);
        then(zoneRepository).should(times(1)).findZoneById(anyLong());
        then(zoneRepository).should(times(1)).save(any());
    }

    @DisplayName("Change a zone with invalid zone ID")
    @Test
    void zoneSettingsWithInvalidZoneId() throws Exception {
        //given
        ZoneSettingDTO zoneSettingDTO = new ZoneSettingDTO(2, 1L, 20, true);
        given(zoneRepository.findZoneById(any())).willReturn(null);

        //when
        assertThatThrownBy(() -> {
            zoneService.zoneSettings(zoneSettingDTO);
        })
                //then
                .isInstanceOf(InvalidActionException.class)
                .hasMessage("The operation failed");

        then(zoneRepository).should(times(1)).findZoneById(anyLong());
        then(zoneRepository).should(times(0)).save(any());
    }

    @DisplayName("Get all zones by floor")
    @Test
    void getZonesByFloor() {
        //given
        Zone zone = new Zone(1L, 1, 'A', true, 10, new HashSet<>());
        List<Zone> list = new ArrayList<>();
        list.add(zone);
        ZoneDTO zoneDTO = new ZoneDTO(1L, 1, 'A', true, 10);

        given(zoneRepository.findAllByFloor(any())).willReturn(list);
        given(zoneMapper.zoneToZoneDTO(any())).willReturn(zoneDTO);

        //when
        ZoneListDTO zoneListDTO = zoneService.getZonesByFloor(any());

        //then
        assertThat(zoneListDTO.getZoneDTOList().get(0).getId()).isEqualTo(1);
        assertThat(zoneListDTO.getZoneDTOList().get(0).getFloor()).isEqualTo(1);
        assertThat(zoneListDTO.getZoneDTOList().get(0).getZone()).isEqualTo('A');
        assertThat(zoneListDTO.getZoneDTOList().get(0).isActivated()).isEqualTo(true);
        assertThat(zoneListDTO.getZoneDTOList().get(0).getCapacity()).isEqualTo(10);
        then(zoneRepository).should(times(1)).findAllByFloor(any());
        then(zoneMapper).should(times(1)).zoneToZoneDTO(any());
    }

    @DisplayName("Check if a zone on a day which is full")
    @Test
    void isFullOnADay() throws Exception {
        //given
        Zone zone = new Zone(1L, 1, 'A', true, 1, new HashSet<>());
        List<Booking> list = new ArrayList<>();
        list.add(new Booking());
        given(zoneRepository.findById(any())).willReturn(Optional.of(zone));
        given(bookingRepository.findAllByDateAndZone(any(), any())).willReturn(list);

        //when
        boolean isFull = zoneService.isFullOnADay(1L, any());

        //then
        assertThat(isFull).isEqualTo(true);
        then(zoneRepository).should(times(1)).findById(any());
        then(bookingRepository).should(times(1)).findAllByDateAndZone(any(), any());
    }

    @DisplayName("Check if a zone on a day which is available")
    @Test
    void isAvailableOnADay() throws Exception {
        //given
        Zone zone = new Zone(1L, 1, 'A', true, 2, new HashSet<>());
        List<Booking> list = new ArrayList<>();
        list.add(new Booking());
        given(zoneRepository.findById(any())).willReturn(Optional.of(zone));
        given(bookingRepository.findAllByDateAndZone(any(), any())).willReturn(list);

        //when
        boolean isFull = zoneService.isFullOnADay(1L, any());

        //then
        assertThat(isFull).isEqualTo(false);
        then(zoneRepository).should(times(1)).findById(any());
        then(bookingRepository).should(times(1)).findAllByDateAndZone(any(), any());
    }

    @DisplayName("Check status of a zone on a day")
    @Test
    void checkStatusOfAZoneOnADay() throws Exception {
        //given
        Zone zone = new Zone(1L, 1, 'A', true, 1, new HashSet<>());
        ZoneDateDTO zoneDateDTO = new ZoneDateDTO(1L, LocalDate.of(2020, 11, 11));
        List<Booking> list = new ArrayList<>();
        list.add(new Booking());
        given(zoneRepository.getOne(any())).willReturn(zone);
        given(zoneRepository.findById(any())).willReturn(Optional.of(zone));
        given(bookingRepository.findAllByDateAndZone(any(), any())).willReturn(list);


        //when
        StatusOfAZoneOnADayDTO statusOfAZoneOnADayDTO = zoneService.checkStatusOfAZoneOnADay(zoneDateDTO);

        //then
        assertThat(statusOfAZoneOnADayDTO.getTotalReservation()).isEqualTo(1);
        assertThat(statusOfAZoneOnADayDTO.getCapacity()).isEqualTo(1);
        assertThat(statusOfAZoneOnADayDTO.getBookedPercentage()).isEqualTo(100);
        then(zoneRepository).should(times(1)).getOne(any());
    }

    @DisplayName("Check status of all zone in a floor")
    @Test
    void checkStatusOfAllZoneInAFloor() throws Exception {
        //given
        Zone zone = new Zone(1L, 1, 'A', true, 10, new HashSet<>());
        List<Zone> list = new ArrayList<>();
        list.add(zone);
        ZoneDTO zoneDTO = new ZoneDTO(1L, 1, 'A', true, 10);

        List<Booking> bookingList = new ArrayList<>();
        Booking booking = new Booking();
        booking.setZone(zone);
        bookingList.add(booking);

        FloorDateDTO floorDateDTO = new FloorDateDTO(1, LocalDate.of(2020, 11, 11));

        given(zoneRepository.findAllByFloor(any())).willReturn(list);
        given(zoneMapper.zoneToZoneDTO(any())).willReturn(zoneDTO);

        given(zoneRepository.findById(any())).willReturn(Optional.of(zone));
        given(bookingRepository.findAllByDateAndZone(any(), any())).willReturn(bookingList);

        //when
        List<StatusOfAZoneOnADayDTO> statusOfAZoneOnADayDTOList = zoneService.checkStatusOfAllZoneInAFloor(floorDateDTO);

        //then
        assertThat(statusOfAZoneOnADayDTOList.get(0).getTotalReservation()).isEqualTo(1);
        assertThat(statusOfAZoneOnADayDTOList.get(0).getCapacity()).isEqualTo(10);
        assertThat(statusOfAZoneOnADayDTOList.get(0).getBookedPercentage()).isEqualTo(10);
        then(zoneRepository).should(times(1)).findAllByFloor(any());
        then(zoneMapper).should(times(1)).zoneToZoneDTO(any());
        then(zoneRepository).should(times(1)).findById(any());
        then(bookingRepository).should(times(1)).findAllByDateAndZone(any(), any());
    }

    @DisplayName("Check status of all floor in a period")
    @Test
    void checkStatusOfAllFloorPeriod() {
        //given
        PeriodDTO periodDTO = new PeriodDTO(LocalDate.of(2020, 11, 11), LocalDate.of(2020, 11, 12));

        List<Booking> bookingList = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            Booking booking = new Booking();
            Zone zone = new Zone(1L, i, 'A', true, 10, new HashSet<>());
            booking.setZone(zone);
            bookingList.add(booking);
        }

        given(bookingRepository.findAllByDateLessThanEqualAndDateGreaterThanEqual(any(), any())).willReturn(bookingList);

        //when
        List<FloorStatusPeriodDTO> floorStatusPeriodDTOList = zoneService.checkStatusOfAllFloorPeriod(periodDTO);

        //then
        assertThat(floorStatusPeriodDTOList.get(0).getTotalBooking()).isEqualTo(1);
        assertThat(floorStatusPeriodDTOList.get(1).getTotalBooking()).isEqualTo(1);
        assertThat(floorStatusPeriodDTOList.get(2).getTotalBooking()).isEqualTo(1);
        assertThat(floorStatusPeriodDTOList.get(3).getTotalBooking()).isEqualTo(1);
        assertThat(floorStatusPeriodDTOList.get(4).getTotalBooking()).isEqualTo(1);
        assertThat(floorStatusPeriodDTOList.get(5).getTotalBooking()).isEqualTo(1);
        assertThat(floorStatusPeriodDTOList.get(6).getTotalBooking()).isEqualTo(1);
        then(bookingRepository).should(times(1)).findAllByDateLessThanEqualAndDateGreaterThanEqual(any(), any());
    }

    @DisplayName("Get total booking of a day in a zone")
    @Test
    void getTotalBookingOfADayInAZone() throws Exception {
        //given
        Zone zone = new Zone(1L, 1, 'A', true, 1, new HashSet<>());
        List<Booking> list = new ArrayList<>();
        list.add(new Booking());
        given(zoneRepository.findById(any())).willReturn(Optional.of(zone));
        given(bookingRepository.findAllByDateAndZone(any(), any())).willReturn(list);


        //when
        int total = zoneService.getTotalBookingOfADayInAZone(1L, any());

        //then
        assertThat(total).isEqualTo(1);
        then(zoneRepository).should(times(1)).findById(any());
        then(bookingRepository).should(times(1)).findAllByDateAndZone(any(), any());
    }
}