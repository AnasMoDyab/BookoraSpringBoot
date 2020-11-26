package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * A DTO that transfers a booking ID.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteBookingByIdDTO {
    @NotNull
    private Long bookingId;

}
