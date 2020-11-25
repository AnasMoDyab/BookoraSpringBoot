package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO that used to transfer email and roles of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private String email;
    private Set<String> role;

}
