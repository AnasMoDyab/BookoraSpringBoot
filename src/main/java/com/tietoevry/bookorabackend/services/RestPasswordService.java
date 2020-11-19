package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;

public interface RestPasswordService {
    MessageDTO updatePassword(UpdatePasswordDTO updatePasswordDTO) throws EmployeeNotFoundException, Exception;

    boolean checkCode(String codeConfirmationDto);
}
