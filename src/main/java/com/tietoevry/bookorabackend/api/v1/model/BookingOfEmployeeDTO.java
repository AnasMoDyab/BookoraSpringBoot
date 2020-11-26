package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * A DTO that transfer information about a booking of a employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingOfEmployeeDTO {
    private Long bookingId;
    private LocalDate date;
    private Character zoneName;
    private Integer floor;
    @NotNull
    @Size(max = 50)
    @Email
    private String email;
}

