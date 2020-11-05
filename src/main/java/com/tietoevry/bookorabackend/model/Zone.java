package com.tietoevry.bookorabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer floor;
    private Character zone;
    private boolean activated;
    private Integer capacity;

    @OneToMany(mappedBy = "zone", fetch = FetchType.LAZY)
    private Set<Booking> zoneBookings;

    public Zone(Integer floor, Character zone, Boolean activated, Integer capacity) {
        this.floor = floor;
        this.zone = zone;
        this.activated = activated;
        this.capacity = capacity;
    }
}
