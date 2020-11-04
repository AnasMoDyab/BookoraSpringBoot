package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;

public interface ConfirmationTokenService {
    String checkToken(String confirmationToken);
}
