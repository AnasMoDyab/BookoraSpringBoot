package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * A DTO that transfers a period.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeriodDTO {
    @NotNull
    private LocalDate from;
    @NotNull
    private LocalDate to;

}
