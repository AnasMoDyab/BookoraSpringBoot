package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.services.RestPasswordTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestpasswordTokenController {
    private final RestPasswordTokenService restPasswordTokenService;

    public RestpasswordTokenController(RestPasswordTokenService restPasswordTokenService) {
        this.restPasswordTokenService = restPasswordTokenService;
    }

    @GetMapping("/confirm-reset")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO responsePasswordRest(@RequestParam("token") String token/*, HttpServletResponse response*/) {
        //  return confirmationTokenService.checkToken(token); //TODO not complete
      //  System.out.println(response);
        return restPasswordTokenService.checkToken(token);
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public MessageDTO resetUserPassword(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        return restPasswordTokenService.updatePassword(updatePasswordDTO);
    }
}


