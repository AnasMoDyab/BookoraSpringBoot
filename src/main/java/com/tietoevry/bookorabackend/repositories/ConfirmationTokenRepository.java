package com.tietoevry.bookorabackend.repositories;

import com.tietoevry.bookorabackend.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This repository provides create, read, update and delete operations for confirmation token.
 */
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    /**
     * Provides a confirmation token object based on a confirmation token string.
     *
     * @param confirmationToken A confirmation token string
     * @return A confirmation token object
     */
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}
