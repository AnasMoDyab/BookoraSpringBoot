package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that transfers to server message, and bookingId.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingIdDTO {

    String message;
    Long bookingId;
}
