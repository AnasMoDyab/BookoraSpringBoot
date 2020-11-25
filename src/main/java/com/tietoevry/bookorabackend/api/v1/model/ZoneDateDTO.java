package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO that used to transfer zoneId,and date.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDateDTO {

    private Long zoneId;
    private LocalDate date;
}
