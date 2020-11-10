package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusOfAZoneOnADayDTO {
    int totalReservation;
    int capacity;
    int bookedPercentage;

    public StatusOfAZoneOnADayDTO(int totalReservation, int capacity) {
        this.totalReservation = totalReservation;
        this.capacity = capacity;
        this.bookedPercentage = (totalReservation*100)/capacity;
    }
}
