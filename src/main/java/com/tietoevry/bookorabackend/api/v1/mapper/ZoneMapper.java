package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.model.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ZoneMapper {

    ZoneMapper INSTANCE = Mappers.getMapper(ZoneMapper.class);

    ZoneDTO zoneToZoneDTO(Zone zone);

    @Mapping(target = "zoneBookings", ignore = true)
    Zone zoneDTOtoZone(ZoneDTO zoneDTO);
}

