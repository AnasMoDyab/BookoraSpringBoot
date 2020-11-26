package com.tietoevry.bookorabackend.api.v1.model;

import com.tietoevry.bookorabackend.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * A DTO that transfers information, including email and password of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

}
