package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO that transfers total bookings in the whole building.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalBookingInBuildingDTO {
    private Integer totalForAllFloor;
}
