package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO that used to transfer token,id,email, and roles of employee.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private List<String> roles;

    public JwtDTO(String accessToken, Long id, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public JwtDTO(String accessToken) {
        this.token = accessToken;
    }
}

