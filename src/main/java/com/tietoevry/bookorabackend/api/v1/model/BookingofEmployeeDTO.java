package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookingofEmployeeDTO {
    private Long bookingId;
    private LocalDate date;
    private Character zoneName;
    private Integer floor;
    private String email;
}

