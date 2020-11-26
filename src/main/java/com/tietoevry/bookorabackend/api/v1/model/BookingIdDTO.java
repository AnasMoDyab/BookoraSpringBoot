package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * A DTO that transfer information, including server message and bookingId.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingIdDTO {
    @NotNull
    String message;
    @NotNull
    Long bookingId;
}
