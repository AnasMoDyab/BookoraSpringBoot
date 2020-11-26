package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO that transfers information, including floor and total bookings.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorStatusPeriodDTO {
    private Integer floor;
    private Integer TotalBooking;
}
