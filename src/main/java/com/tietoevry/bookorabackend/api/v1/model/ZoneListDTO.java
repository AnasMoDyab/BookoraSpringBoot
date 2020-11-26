package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * A DTO that transfers a list of ZoneDTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneListDTO {

    List<ZoneDTO> zoneDTOList;
}
