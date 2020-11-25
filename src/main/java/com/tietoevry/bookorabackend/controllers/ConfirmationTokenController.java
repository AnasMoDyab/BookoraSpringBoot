package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.services.ConfirmationTokenService;
import com.tietoevry.bookorabackend.services.EmployeeServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A controller that provides API for email verification after sign up.
 *
 * <p>The controller redirects user to different pages based on the confirmation token that is sent.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class ConfirmationTokenController {

    private final ConfirmationTokenService confirmationTokenService;

    public ConfirmationTokenController(ConfirmationTokenService confirmationTokenService, EmployeeServiceImp employeeServiceImp) {
        this.confirmationTokenService = confirmationTokenService;
    }

    /**
     * Verifies confirmation token.
     *
     * <p>User will be redirected to different in pages pages based on the confirmation token that is sent.
     *
     * @param token A confirmation token string
     * @return A DTO that contains message about the result of verification
     */
    @GetMapping("/confirm-account")
    @ResponseStatus(HttpStatus.OK)
    public String messageDTO(@RequestParam("token") String token) {
        return confirmationTokenService.checkToken(token); //TODO not complete
    }
}
