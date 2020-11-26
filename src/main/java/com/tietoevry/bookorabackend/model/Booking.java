package com.tietoevry.bookorabackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity that represents a booking.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"employee", "zone"})
@EqualsAndHashCode(exclude = {"employee", "zone"})
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Zone zone;

    public Booking(LocalDate date, Employee employee, Zone zone) {
        this.date = date;
        this.employee = employee;
        this.zone = zone;
    }
}
