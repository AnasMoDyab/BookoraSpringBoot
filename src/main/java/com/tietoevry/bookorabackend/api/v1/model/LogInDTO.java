package com.tietoevry.bookorabackend.api.v1.model;

import com.tietoevry.bookorabackend.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * A DTO that transfers information, including email and password of employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogInDTO {

    @NotNull(message = "You must fill in email.")
    @Email
    private String email;
    @NotNull(message = "You must fill in first password.")
    @NotBlank
    @ValidPassword
    private String password;

}
