package com.tietoevry.bookorabackend.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity that used for employee info.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"roles", "confirmationToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName;


    private String lastName;

    @EqualsAndHashCode.Include
    private String email;


    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<Booking> employeeBookings = new HashSet<>();

    private boolean isEnabled;

    private boolean ableToChangePassword;


    @OneToMany(mappedBy = "employee")
    private Set<ConfirmationToken> confirmationToken = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<RestPasswordCode> restPasswordCode = new HashSet<>();

    public Employee(@NotNull(message = "You must fill in first name.") String firstName, @NotNull(message = "You must fill in last name.") String lastName, @NotNull(message = "You must fill in e-mail.") @Email String email, @NotNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
