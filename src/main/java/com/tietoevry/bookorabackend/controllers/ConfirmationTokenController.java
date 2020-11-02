package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.ReActiveEmailDTO;
import com.tietoevry.bookorabackend.services.ConfirmationTokenService;
import com.tietoevry.bookorabackend.services.EmployeeServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ConfirmationTokenController {

    private final ConfirmationTokenService confirmationTokenService;



    public ConfirmationTokenController(ConfirmationTokenService confirmationTokenService, EmployeeServiceImp employeeServiceImp) {
        this.confirmationTokenService = confirmationTokenService;


    }

    @GetMapping("/confirm-account")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO messageDTO(@RequestParam("token") String token){
        return confirmationTokenService.checkToken(token); //TODO not complete
    }











}
