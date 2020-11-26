package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.RestPasswordCode;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository provides create, read, update and delete operations for activation codes for resetting password.
 */
public interface RestPasswordCodeRepository extends JpaRepository<RestPasswordCode, Long> {

    /**
     * Provides a RestPasswordCode object based on a activation code string.
     *
     * @param confirmationCode A activation code string
     * @return A RestPasswordCode object
     */
    RestPasswordCode findByConfirmationCode(String confirmationCode);

}
