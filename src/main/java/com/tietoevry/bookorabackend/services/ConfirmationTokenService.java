package com.tietoevry.bookorabackend.services;

/**
 * This interface provides service for email verification after sign up.
 */
public interface ConfirmationTokenService {

    /**
     * Verifies confirmation token.
     *
     * <p>User will be redirected to different in pages pages based on the confirmation token that is sent.
     *
     * @param confirmationToken A confirmation token string
     * @return A DTO that contains message about the result of verification
     */
    String checkToken(String confirmationToken);
}
