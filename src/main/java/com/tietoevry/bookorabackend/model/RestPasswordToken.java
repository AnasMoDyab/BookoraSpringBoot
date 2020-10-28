package com.tietoevry.bookorabackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RestPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String restPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne(fetch = FetchType.EAGER)
    private Employee employee;

    private Timestamp expiryDate;

    public RestPasswordToken(Employee employee) {
        this.employee = employee;
        createdDate = new Date();
        restPasswordToken = UUID.randomUUID().toString();
    }
}


