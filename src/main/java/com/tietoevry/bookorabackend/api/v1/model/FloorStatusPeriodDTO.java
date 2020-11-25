package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that transfers floor, and total of booking.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorStatusPeriodDTO {
    private Integer floor;
    private Integer TotalBooking;
}
