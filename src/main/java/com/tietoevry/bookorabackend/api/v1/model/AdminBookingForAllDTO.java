package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO that  transfers  a period.
 */
public class AdminBookingForAllDTO {
    private LocalDate from;
    private LocalDate to;
}
