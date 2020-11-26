package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * A DTO that transfers email of a employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEmailDTO {

    @Email
    @NotNull
    private String email;

}