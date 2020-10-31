package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.CodeConfirmationDto;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.services.RestPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestpasswordController {
    private final RestPasswordService restPasswordService;

    public RestpasswordController(RestPasswordService restPasswordService) {
        this.restPasswordService = restPasswordService;
    }

   @GetMapping("/confirm-reset")
    @ResponseStatus(HttpStatus.OK)
    public boolean responsePasswordRest(@RequestBody @Valid CodeConfirmationDto codeConfirmationDto) {
        //  return confirmationTokenService.checkToken(token); //TODO not complete
        return restPasswordService.checkCode(codeConfirmationDto);
       //return new ModelAndView("redirect:http://localhost:3000");

    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public MessageDTO resetUserPassword(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        return restPasswordService.updatePassword(updatePasswordDTO);
    }
}


