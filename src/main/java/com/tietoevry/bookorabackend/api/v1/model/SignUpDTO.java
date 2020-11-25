package com.tietoevry.bookorabackend.api.v1.model;

import com.tietoevry.bookorabackend.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * DTO that used to transfer employee info.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {

    @NotBlank
    private String lastName;

    @NotBlank
    private String firstName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @ValidPassword
    private String password;

    private Set<String> roles;
}
