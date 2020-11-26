package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * A DTO that transfers information, including  zone ID and a date.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDateDTO {

    private Long zoneId;
    private LocalDate date;
}
