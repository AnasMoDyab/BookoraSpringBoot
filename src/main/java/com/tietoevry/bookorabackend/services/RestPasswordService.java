package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.CodeConfirmationDto;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface RestPasswordService {
    MessageDTO  updatePassword(UpdatePasswordDTO updatePasswordDTO);
    boolean checkCode(CodeConfirmationDto codeConfirmationDto);
}
