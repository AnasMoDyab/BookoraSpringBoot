package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.services.ConfirmationTokenService;
import com.tietoevry.bookorabackend.services.RestPasswordTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConfirmationTokenController {

    private final ConfirmationTokenService confirmationTokenService;
    private final RestPasswordTokenService restPasswordTokenService;

    public ConfirmationTokenController(ConfirmationTokenService confirmationTokenService, RestPasswordTokenService restPasswordTokenService) {
        this.confirmationTokenService = confirmationTokenService;
        this.restPasswordTokenService = restPasswordTokenService;
    }

    @GetMapping("/confirm-account")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO messageDTO(@RequestParam("token") String token){
        return confirmationTokenService.checkToken(token); //TODO not complete
    }









}
