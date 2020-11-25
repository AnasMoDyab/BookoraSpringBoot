package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that used to delete a booking by bookingId.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookingByIdDTO {
    private long bookingId;

}
