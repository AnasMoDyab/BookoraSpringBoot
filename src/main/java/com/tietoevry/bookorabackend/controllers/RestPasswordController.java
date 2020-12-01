package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.services.RestPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * A rest controller that provides API for resetting password.
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class RestPasswordController {
    private final RestPasswordService restPasswordService;

    public RestPasswordController(RestPasswordService restPasswordService) {
        this.restPasswordService = restPasswordService;
    }

    /**
     * Checks activation code for resetting password.
     *
     * @param codeConfirmationDto A string of activation code for resetting password
     * @return True if the activation is valid
     */
    @GetMapping("/confirm-reset")
    @ResponseStatus(HttpStatus.OK)
    public boolean responsePasswordRest(@RequestParam("codeConfirmationDto") String codeConfirmationDto) {
        return restPasswordService.checkCode(codeConfirmationDto);
    }

    /**
     * Updates the password of a employee.
     *
     * @param logInDTO A DTO that contains email of employee and the new password
     * @return A DTO that contains message about the password update action
     * @throws Exception EmployeeNotFoundException if email is not found
     * @throws Exception InvalidInputException if the new password is the same as the previous
     * @throws Exception InvalidActionException if the employee is not allowed to change the password
     */
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public MessageDTO resetUserPassword(@RequestBody @Valid LogInDTO logInDTO) throws Exception {
        return restPasswordService.updatePassword(logInDTO);
    }
}


