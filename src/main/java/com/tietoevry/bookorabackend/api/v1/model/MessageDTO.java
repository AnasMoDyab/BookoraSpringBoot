package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO that transfers information, including a message.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String message;
}
