package com.tietoevry.bookorabackend.api.v1.model;

import com.tietoevry.bookorabackend.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * A DTO that transfers information about the registering employee.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    @NotNull(message = "You must fill in first name.")
    private String firstName;

    @NotNull(message = "You must fill in last name.")
    private String lastName;
    @NotNull
    @Size(max = 50)
    @Email
    private String email;
    @NotNull(message = "You must fill in first password.")
    @ValidPassword
    private String password;

    private Set<String> roles;
}
