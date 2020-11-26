package com.tietoevry.bookorabackend.api.v1.mapper;

import com.tietoevry.bookorabackend.api.v1.model.ZoneDTO;
import com.tietoevry.bookorabackend.model.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper that maps between Zone entity and DTOs.
 */
@Mapper
public interface ZoneMapper {

    ZoneMapper INSTANCE = Mappers.getMapper(ZoneMapper.class);

    /**
     * Maps ZoneDTO to Zone entity.
     *
     * @param zone A zone object
     * @return ZoneDTO that contains zone ID, floor, capacity and status of zone
     */
    ZoneDTO zoneToZoneDTO(Zone zone);
}

