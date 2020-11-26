package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * A DTO that transfers information, including a specific date, employee ID and zone ID.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    @NotNull
    private LocalDate date;
    @NotNull
    private Long employeeId;
    @NotNull
    private Long zoneId;

}
