package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;

public interface RestPasswordService {
    MessageDTO updatePassword(UpdatePasswordDTO updatePasswordDTO);

    boolean checkCode(String codeConfirmationDto);
}
