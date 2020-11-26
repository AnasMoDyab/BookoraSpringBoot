package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO that transfers a booking ID.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookingByIdDTO {
    private long bookingId;

}
