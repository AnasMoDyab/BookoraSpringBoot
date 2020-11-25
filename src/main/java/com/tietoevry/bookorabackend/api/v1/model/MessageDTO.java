package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that used to transfer message to server.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String message;
}
