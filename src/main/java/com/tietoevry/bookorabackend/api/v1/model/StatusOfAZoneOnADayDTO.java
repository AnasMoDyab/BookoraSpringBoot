package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO that transfers a status of zone on a day.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusOfAZoneOnADayDTO {
    Long id;
    int totalReservation;
    int capacity;
    int bookedPercentage;

    public StatusOfAZoneOnADayDTO(int totalReservation, int capacity) {
        this.totalReservation = totalReservation;
        this.capacity = capacity;
        this.bookedPercentage = (totalReservation * 100) / capacity;
    }

    public StatusOfAZoneOnADayDTO(int totalReservation, int capacity, Long id) {
        this.id = id;
        this.totalReservation = totalReservation;
        this.capacity = capacity;
        this.bookedPercentage = (totalReservation * 100) / capacity;
    }
}
