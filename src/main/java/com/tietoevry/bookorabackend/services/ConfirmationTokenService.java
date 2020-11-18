package com.tietoevry.bookorabackend.services;

public interface ConfirmationTokenService {
    String checkToken(String confirmationToken);
}
