package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.services.RestPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestPasswordController {
    private final RestPasswordService restPasswordService;

    public RestPasswordController(RestPasswordService restPasswordService) {
        this.restPasswordService = restPasswordService;
    }

    //Use to check the activation code for resetting password
    @GetMapping("/confirm-reset")
    @ResponseStatus(HttpStatus.OK)
    public boolean responsePasswordRest(@RequestParam("codeConfirmationDto") String codeConfirmationDto) {
        //  return confirmationTokenService.checkToken(token); //TODO not complete
        return restPasswordService.checkCode(codeConfirmationDto);
        //return new ModelAndView("redirect:http://localhost:3000");

    }

    //Use to update password
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public MessageDTO resetUserPassword(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) throws Exception {
        return restPasswordService.updatePassword(updatePasswordDTO);
    }
}


