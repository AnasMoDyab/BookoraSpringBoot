package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * This repository provides create, read, update and delete operations for employee.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Provides a Employee object based on an email string.
     *
     * @param email A email string
     * @return A Employee object
     */
    Employee findByEmailIgnoreCase(String email);

    /**
     * Provides a Optional employee object based on an email string.
     *
     * @param email A email string
     * @return A Optional employee object
     */
    Optional<Employee> findByEmail(String email);
}
