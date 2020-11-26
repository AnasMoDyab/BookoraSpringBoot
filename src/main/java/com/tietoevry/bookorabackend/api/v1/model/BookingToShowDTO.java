package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * A DTO that transfers information, including booking ID, a date, zone name and floor.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingToShowDTO {
    private Long bookingId;
    private LocalDate date;
    private Character zoneName;
    private Integer floor;
}
