package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO that used to transfer a period and email of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBookingInAPeriodDTO {

    private String email;
    private LocalDate from;
    private LocalDate to;

}
