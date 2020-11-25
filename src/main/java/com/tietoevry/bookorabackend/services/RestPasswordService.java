package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;

/**
 * This interface provides services for resetting password.
 */
public interface RestPasswordService {

    /**
     * Updates the password of a employee.
     *
     * @param logInDTO A DTO that contains email of employee and the new password
     * @return A DTO that contains message about the password update action
     * @throws Exception EmployeeNotFoundException if email is not found
     * @throws Exception InvalidInputException if the new password is the same as the previous
     * @throws Exception InvalidActionException if the employee is not allowed to change the password
     */
    MessageDTO updatePassword(LogInDTO logInDTO) throws Exception;

    /**
     * Checks activation code for resetting password.
     *
     * @param codeConfirmationDto A string of activation code for resetting password
     * @return True if the activation is valid
     */
    boolean checkCode(String codeConfirmationDto);
}
