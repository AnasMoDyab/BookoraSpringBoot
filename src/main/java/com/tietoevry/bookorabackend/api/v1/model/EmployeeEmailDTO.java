package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO that used to transfer email of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEmailDTO {


    @Email
    @NotBlank
    private String email;

}