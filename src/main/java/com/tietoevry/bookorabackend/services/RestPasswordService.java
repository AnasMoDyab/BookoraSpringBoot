package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;

public interface RestPasswordService {
    MessageDTO updatePassword(LogInDTO logInDTO) throws Exception;

    boolean checkCode(String codeConfirmationDto);
}
