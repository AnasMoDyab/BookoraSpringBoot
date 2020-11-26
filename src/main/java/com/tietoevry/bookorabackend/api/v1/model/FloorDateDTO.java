package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * A DTO that transfers information, including floor ID and a date.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FloorDateDTO {
    @NotNull
    private Integer floorId;
    @NotNull
    private LocalDate date;
}
