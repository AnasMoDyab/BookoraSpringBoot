package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * DTO that used to transfer email and roles of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private Set<String> role;

}
