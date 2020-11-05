package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.ZoneMapper;
import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.api.v1.model.ZoneListDTO;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.BookingRepository;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public ZoneListDTO getAllZones() {
        List<ZoneDTO> zoneListDTO = zoneRepository.findAll().stream().map(zone -> {
            ZoneDTO zoneDTO = zoneMapper.zoneToZoneDTO(zone);
            return zoneDTO;
        }).collect(Collectors.toList());
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

    @Override
    public int getTotalBookingOfADayInAZone(Long id, LocalDate date) {
        Zone zone = zoneRepository.findById(id).orElseThrow(() -> new RuntimeException("zone id is not found"));
        return bookingRepository.findAllByDateAndZone(date, zone).size();
    }
}
