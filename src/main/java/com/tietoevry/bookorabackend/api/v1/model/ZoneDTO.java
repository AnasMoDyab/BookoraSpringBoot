package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that used to transfer zone info.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDTO {

    private Long id;
    private Integer floor;
    private Character zone;
    private boolean activated;
    private Integer capacity;
}
