package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface RestPasswordTokenService {
    MessageDTO checkToken(String confirmationToken);
    MessageDTO  updatePassword(UpdatePasswordDTO updatePasswordDTO);
}
