package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.ZoneMapper;
import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.model.Booking;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public MessageDTO ZoneSettings(ZoneSettingDTO zoneSettingDTO) {
        Zone zoneToChange = zoneRepository.findZoneById(zoneSettingDTO.getZoneId());
        if(zoneToChange != null){

            zoneToChange.setActivated(zoneSettingDTO.isActivated());
            zoneToChange.setCapacity(zoneSettingDTO.getCapacity());
            zoneToChange.setFloor(zoneSettingDTO.getFloor());
            zoneRepository.save(zoneToChange );
            return new MessageDTO("Modified successfully");
        }
        return new MessageDTO("The operation falid");
    }

    @Override
    public ZoneListDTO getAllZones() {
        List<ZoneDTO> zoneListDTO = new ArrayList<>();
        for (Zone zone : zoneRepository.findAll()) {
            ZoneDTO zoneDTO = zoneMapper.zoneToZoneDTO(zone);
            zoneListDTO.add(zoneDTO);
        }
        return new ZoneListDTO(zoneListDTO);
    }

    @Override
    public ZoneListDTO getZonesByFloor(Integer floor) {
        List<ZoneDTO> zoneListDTO = new ArrayList<>();
        for (Zone zone : zoneRepository.findAllByFloor(floor)) {
            ZoneDTO zoneDTO = zoneMapper.zoneToZoneDTO(zone);
            zoneListDTO.add(zoneDTO);
        }
        return new ZoneListDTO(zoneListDTO);
    }

    @Override
    public ZoneDTO getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("zone id is not found"));
        return zoneMapper.zoneToZoneDTO(zone);
    }

    @Override
    public boolean isFullOnADay(Long id, LocalDate date) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("zone id is not found"));

        Integer capacity = zone.getCapacity();

        int totalBooking = bookingRepository.findAllByDateAndZone(date, zone).size();

        return totalBooking == capacity;
    }

    public StatusOfAZoneOnADayDTO checkStatusOfAZoneOnADay(ZoneDateDTO zoneDateDTO) {
        Zone zone = zoneRepository.getOne(zoneDateDTO.getZoneId());
        int total = getTotalBookingOfADayInAZone(zoneDateDTO.getZoneId(), zoneDateDTO.getDate());
        int capacity = zone.getCapacity();

        return new StatusOfAZoneOnADayDTO(total, capacity);
    }

    @Override
    public List<StatusOfAZoneOnADayDTO> checkStatusOfAllZoneInAFloor(FloorDateDTO floorDateDTO) {

        ZoneListDTO zoneListDTO = getZonesByFloor(floorDateDTO.getFloorId());
        List<StatusOfAZoneOnADayDTO> statusOfAZoneOnADayDTOList = new ArrayList<>();

        for(ZoneDTO zoneDTO : zoneListDTO.getZoneDTOList()) {
            int total = getTotalBookingOfADayInAZone(zoneDTO.getId(), floorDateDTO.getDate());
            int capacity = zoneDTO.getCapacity();
            statusOfAZoneOnADayDTOList.add(new StatusOfAZoneOnADayDTO(total, capacity));
        }



        return  statusOfAZoneOnADayDTOList;
    }

    @Override
    public List<FloorStatusPeriodeDTO> checkStatusOfAllFloorPeriode(PeriodeDTO periodeDTO) {

       LocalDate from = periodeDTO.getFrom();
       LocalDate to = periodeDTO.getTo();

     List<Booking> bookingBookingList = bookingRepository.findAllByDateLessThanEqualAndDateGreaterThanEqual(to,from);
     int floor1total = 0,floor2total=0,floor3total=0,floor4total=0,floor5total=0,floor6total=0,floor7total=0;
List<FloorStatusPeriodeDTO> floorStatusPeriodeDTOS = new ArrayList<>();

        for(Booking booking : bookingBookingList){

         Integer floor = booking.getZone().getFloor();

         switch (floor){

             case 1 :
                 floor1total++;

                 break;
             case 2 :
                 floor2total++;
                break;
             case 3 :
                 floor3total++;
                 break;
             case 4 :
                 floor4total++;
                 break;
             case 5 :
                 floor5total++;
                 break;
             case 6 :
                 floor6total++;
                 break;
             case 7 :
                 floor7total++;
                 break;


         }


     }

        FloorStatusPeriodeDTO   floorStatusPeriodeDTO1 = new FloorStatusPeriodeDTO(1,floor1total);
        FloorStatusPeriodeDTO   floorStatusPeriodeDTO2 = new FloorStatusPeriodeDTO(2,floor2total);
        FloorStatusPeriodeDTO   floorStatusPeriodeDTO3 = new FloorStatusPeriodeDTO(3,floor3total);
        FloorStatusPeriodeDTO   floorStatusPeriodeDTO4 = new FloorStatusPeriodeDTO(4,floor4total);
        FloorStatusPeriodeDTO   floorStatusPeriodeDTO5 = new FloorStatusPeriodeDTO(5,floor5total);
        FloorStatusPeriodeDTO   floorStatusPeriodeDTO6 = new FloorStatusPeriodeDTO(6,floor6total);
        FloorStatusPeriodeDTO   floorStatusPeriodeDTO7 = new FloorStatusPeriodeDTO(7,floor7total);
        floorStatusPeriodeDTOS.add(floorStatusPeriodeDTO1);
        floorStatusPeriodeDTOS.add(floorStatusPeriodeDTO2);
        floorStatusPeriodeDTOS.add(floorStatusPeriodeDTO3);
        floorStatusPeriodeDTOS.add(floorStatusPeriodeDTO4);
        floorStatusPeriodeDTOS.add(floorStatusPeriodeDTO5);
        floorStatusPeriodeDTOS.add(floorStatusPeriodeDTO6);
        floorStatusPeriodeDTOS.add(floorStatusPeriodeDTO7);
        return floorStatusPeriodeDTOS;
    }

    @Override
    public TotalBookingInBuildingDTO CheckStatusOfTheBuildingOnPeriode(PeriodeDTO BuildingPeriodeDTO) {
        LocalDate from = BuildingPeriodeDTO.getFrom();
        LocalDate to = BuildingPeriodeDTO.getTo();
        List<Booking> bookingBookingList = bookingRepository.findAllByDateLessThanEqualAndDateGreaterThanEqual(to,from);
        Integer totalBooking = bookingBookingList.size();
        Integer totalCapacity =zoneRepository.selectTotalsCapacities();
        Integer procentUsed = (totalBooking*100)/totalCapacity;
        return  new TotalBookingInBuildingDTO(procentUsed);
    }


    public int getTotalBookingOfADayInAZone(Long id, LocalDate date) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("zone id is not found"));
        return bookingRepository.findAllByDateAndZone(date, zone).size();
    }

}


