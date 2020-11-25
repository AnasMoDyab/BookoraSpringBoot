package com.tietoevry.bookorabackend.services;

import org.springframework.mail.SimpleMailMessage;

/**
 * This interface provides service for sending email.
 */
public interface EmailSenderService {

    /**
     * Sends email.
     *
     * @param email A {@code SimpleMailMessage} object
     */
    void sendEmail(SimpleMailMessage email);
}
