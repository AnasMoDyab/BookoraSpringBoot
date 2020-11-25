package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.model.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper maps between Zone entity and DTOs.
 */
@Mapper
public interface ZoneMapper {

    ZoneMapper INSTANCE = Mappers.getMapper(ZoneMapper.class);

    /**
     * Maps between Zone entity and ZoneDTO
     * @param zone
     * @return ZoneDTO that contains ZoneId,floor,capacity, and status of zone
     */
    ZoneDTO zoneToZoneDTO(Zone zone);

    /**
     * Maps between Zone entity and ZoneDTO.
     * @param zoneDTO
     * @return A zone entity
     */
    @Mapping(target = "zoneBookings", ignore = true)
    Zone zoneDTOtoZone(ZoneDTO zoneDTO);
}

