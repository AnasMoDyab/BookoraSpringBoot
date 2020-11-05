package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.ZoneMapper;
import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.api.v1.model.ZoneListDTO;
import com.tietoevry.bookorabackend.model.Zone;
import com.tietoevry.bookorabackend.repositories.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;

    public ZoneServiceImpl(ZoneRepository zoneRepository, ZoneMapper zoneMapper) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
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
        Zone zone = zoneRepository.findById(id).orElseThrow(RuntimeException::new);
        return zoneMapper.zoneToZoneDTO(zone);
    }
}
