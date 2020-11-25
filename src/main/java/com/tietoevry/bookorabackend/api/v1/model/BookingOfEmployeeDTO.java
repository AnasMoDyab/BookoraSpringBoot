package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * DTO that transfers info about a booking of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingOfEmployeeDTO {
    private Long bookingId;
    private LocalDate date;
    private Character zoneName;
    private Integer floor;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
}

