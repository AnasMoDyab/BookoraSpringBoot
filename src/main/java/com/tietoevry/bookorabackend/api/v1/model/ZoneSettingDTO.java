package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO that transfers information, including settings of a zone.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneSettingDTO {

    private Integer floor;
    private Long zoneId;
    private int capacity;
    private boolean activated;

}
