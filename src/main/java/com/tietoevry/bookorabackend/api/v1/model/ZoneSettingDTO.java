package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * A DTO that transfers information, including settings of a zone.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneSettingDTO {
    @NotNull
    private Integer floor;
    @NotNull
    private Long zoneId;
    @NotNull
    private int capacity;
    @NotNull
    private boolean activated;

}
