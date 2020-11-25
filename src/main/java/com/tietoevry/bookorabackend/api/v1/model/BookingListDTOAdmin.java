package com.tietoevry.bookorabackend.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *DTO that contains a list of BookingOfEmployeeDTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookingListDTOAdmin {
    List<BookingOfEmployeeDTO> bookingOfEmployeeDTOList;
}
