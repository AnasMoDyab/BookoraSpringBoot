package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.ZoneMapper;
import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.InvalidActionException;
import com.tietoevry.bookorabackend.exception.ZoneNotFoundException;
import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;
    private final BookingRepository bookingRepository;

    public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneMapper zoneMapper, BookingRepository bookingRepository) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public MessageDTO zoneSettings(ZoneSettingDTO zoneSettingDTO) throws Exception {
        Zone zoneToChange = zoneRepository.findZoneById(zoneSettingDTO.getZoneId());
        if (zoneToChange != null) {

            zoneToChange.setActivated(zoneSettingDTO.isActivated());
            zoneToChange.setCapacity(zoneSettingDTO.getCapacity());
            zoneToChange.setFloor(zoneSettingDTO.getFloor());
            zoneRepository.save(zoneToChange);
            return new MessageDTO("Modified successfully");
        }
        throw new InvalidActionException("The operation failed");
    }

/*    @Override
    public ZoneListDTO getAllZones() {
        List<ZoneDTO> zoneListDTO = new ArrayList<>();
        for (Zone zone : zoneRepository.findAll()) {
            ZoneDTO zoneDTO = zoneMapper.zoneToZoneDTO(zone);
            zoneListDTO.add(zoneDTO);
        }
        return new ZoneListDTO(zoneListDTO);
    }*/

    @Override
    public ZoneListDTO getZonesByFloor(Integer floor) {
        List<ZoneDTO> zoneListDTO = new ArrayList<>();
        for (Zone zone : zoneRepository.findAllByFloor(floor)) {
            ZoneDTO zoneDTO = zoneMapper.zoneToZoneDTO(zone);
            zoneListDTO.add(zoneDTO);
        }

        Comparator<ZoneDTO> compareById = (ZoneDTO zone1, ZoneDTO zone2) ->
                zone1.getId().compareTo(zone2.getId());

        Collections.sort(zoneListDTO, compareById);
        return new ZoneListDTO(zoneListDTO);
    }

/*    @Override
    public ZoneDTO getZoneById(Long id) throws Exception {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException("zone id is not found"));
        return zoneMapper.zoneToZoneDTO(zone);
    }*/

    @Override
    public boolean isFullOnADay(Long id, LocalDate date) throws Exception {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException("zone id is not found"));

        Integer capacity = zone.getCapacity();

        int totalBooking = bookingRepository.findAllByDateAndZone(date, zone).size();

        return totalBooking == capacity;
    }

    public StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay(ZoneDateDTO zoneDateDTO) throws Exception {
        Zone zone = zoneRepository.getOne(zoneDateDTO.getZoneId());
        int total = getTotalBookingOfADayInAZone(zoneDateDTO.getZoneId(), zoneDateDTO.getDate());
        int capacity = zone.getCapacity();

        return new StatusOfAZoneOnADayDTO(total, capacity);
    }

    @Override
    public List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor(FloorDateDTO floorDateDTO) throws Exception {

        ZoneListDTO zoneListDTO = getZonesByFloor(floorDateDTO.getFloorId());
        List<StatusOfAZoneOnADayDTO> statusOfAZoneOnADayDTOList = new ArrayList<>();

        for (ZoneDTO zoneDTO : zoneListDTO.getZoneDTOList()) {
            int total = getTotalBookingOfADayInAZone(zoneDTO.getId(), floorDateDTO.getDate());
            int capacity = zoneDTO.getCapacity();
            Long zoneId = zoneDTO.getId();
            statusOfAZoneOnADayDTOList.add(new StatusOfAZoneOnADayDTO(total, capacity,zoneId));
        }


        Comparator<StatusOfAZoneOnADayDTO> compareById = (StatusOfAZoneOnADayDTO zone1, StatusOfAZoneOnADayDTO zone2) ->
                zone1.getId().compareTo(zone2.getId());

        Collections.sort(statusOfAZoneOnADayDTOList, compareById);

        return statusOfAZoneOnADayDTOList;
    }

    @Override
    public List<FloorStatusPeriodDTO> checkStatusOfAllFloorPeriod(PeriodDTO periodDTO) {

        LocalDate from = periodDTO.getFrom();
        LocalDate to = periodDTO.getTo();

        List<Booking> bookingBookingList = bookingRepository.findAllByDateLessThanEqualAndDateGreaterThanEqual(to, from);
        int floor1total = 0, floor2total = 0, floor3total = 0, floor4total = 0, floor5total = 0, floor6total = 0, floor7total = 0;
        List<FloorStatusPeriodDTO> floorStatusPeriodDTOS = new ArrayList<>();

        for (Booking booking : bookingBookingList) {

            Integer floor = booking.getZone().getFloor();

            switch (floor) {

                case 1:
                    floor1total++;
                    break;
                case 2:
                    floor2total++;
                    break;
                case 3:
                    floor3total++;
                    break;
                case 4:
                    floor4total++;
                    break;
                case 5:
                    floor5total++;
                    break;
                case 6:
                    floor6total++;
                    break;
                case 7:
                    floor7total++;
                    break;
            }
        }

        FloorStatusPeriodDTO floorStatusPeriodDTO1 = new FloorStatusPeriodDTO(1, floor1total);
        FloorStatusPeriodDTO floorStatusPeriodDTO2 = new FloorStatusPeriodDTO(2, floor2total);
        FloorStatusPeriodDTO floorStatusPeriodDTO3 = new FloorStatusPeriodDTO(3, floor3total);
        FloorStatusPeriodDTO floorStatusPeriodDTO4 = new FloorStatusPeriodDTO(4, floor4total);
        FloorStatusPeriodDTO floorStatusPeriodDTO5 = new FloorStatusPeriodDTO(5, floor5total);
        FloorStatusPeriodDTO floorStatusPeriodDTO6 = new FloorStatusPeriodDTO(6, floor6total);
        FloorStatusPeriodDTO floorStatusPeriodDTO7 = new FloorStatusPeriodDTO(7, floor7total);
        floorStatusPeriodDTOS.add(floorStatusPeriodDTO1);
        floorStatusPeriodDTOS.add(floorStatusPeriodDTO2);
        floorStatusPeriodDTOS.add(floorStatusPeriodDTO3);
        floorStatusPeriodDTOS.add(floorStatusPeriodDTO4);
        floorStatusPeriodDTOS.add(floorStatusPeriodDTO5);
        floorStatusPeriodDTOS.add(floorStatusPeriodDTO6);
        floorStatusPeriodDTOS.add(floorStatusPeriodDTO7);
        return floorStatusPeriodDTOS;
    }

/*
    @Override
    public TotalBookingInBuildingDTO CheckStatusOfTheBuildingOnPeriod(PeriodDTO buildingPeriodDTO) {
        LocalDate from = buildingPeriodDTO.getFrom();
        LocalDate to = buildingPeriodDTO.getTo();
        List<Booking> bookingBookingList = bookingRepository.findAllByDateLessThanEqualAndDateGreaterThanEqual(to, from);
        Integer totalBooking = bookingBookingList.size();
        Integer totalCapacity = zoneRepository.selectTotalsCapacities();
        Integer percentUsed = (totalBooking * 100) / totalCapacity;
        return new TotalBookingInBuildingDTO(percentUsed);
    }
*/

    public int getTotalBookingOfADayInAZone(Long id, LocalDate date) throws Exception {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException("zone id is not found"));
        return bookingRepository.findAllByDateAndZone(date, zone).size();
    }

}