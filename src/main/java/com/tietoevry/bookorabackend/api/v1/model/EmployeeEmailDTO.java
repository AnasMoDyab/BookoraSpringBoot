package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that used to transfer email of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEmailDTO {

    private String email;

}