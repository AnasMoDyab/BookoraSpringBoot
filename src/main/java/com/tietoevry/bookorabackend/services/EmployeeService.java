package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;

/**
 * This interface provides services for managing employees.
 */
public interface EmployeeService {
    //EmployeeListDTO getAllEmployees();

    //EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException;

    /**
     * Provides information about a selected employee.
     *
     * @param email A string of email
     * @return An EmployeeDTO that contains information of the employee including ID, first name, last name, email,
     * password and role
     * @throws Exception EmployeeNotFoundException if employee is not found
     */
    EmployeeDTO getEmployeeByEmail(String email) throws Exception;

    /**
     * Create a new employee.
     *
     * @param signUpDTO A SignUpDTO that contains information about employee, including last name, first name, email,
     *                  password and set of roles
     * @return A MessageDTO that contains message string about the creation of a new employee
     * @throws Exception InvalidDomainException if the email domain is not valid
     */
    MessageDTO createNewEmployee(SignUpDTO signUpDTO) throws Exception;

    /**
     * Signs in a employee.
     *
     * @param logInDTO A LoginDTO that contains email and password of the employee
     * @return A JwtDTO that contains information including token string, token type, token ID, email of the employee
     * and list of roles of the employee
     * @throws Exception EmployeeNotFoundException if the employee is not found
     * @throws Exception EmployeeNotActivatedException if the email account is not activated
     */
    JwtDTO logIn(LogInDTO logInDTO) throws Exception;

    /**
     * Updates an employee.
     *
     * @param emailDTO A EmailDTO that contains email of the employee and the set of roles
     * @return A MessageDTO that contains message about the updating
     * @throws Exception EmployeeNotFoundException if the employee is not found
     */
    MessageDTO updateEmployee(EmailDTO emailDTO) throws Exception;

    /**
     * Requests an activation code to for resetting password.
     *
     * <p>The activation code is sent though email.
     *
     * @param employeeEmailDTO A DTO that contains an email string
     * @return A MessageDTO that contains message about the request
     * @throws EmployeeNotFoundException if the employee is not found
     */
    MessageDTO sendForgetPasswordCode(EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException;

    /**
     * Requests for reseeding an activation link for an account.
     *
     * <p>The activation link is sent though email.
     *
     * @param employeeEmailDTO A DTO that contains an email string
     * @return A MessageDTO that contains message about the request
     * @throws EmployeeNotFoundException if the employee is not found
     */
    MessageDTO resendConfirmationToken(EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException;


    //EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO);

    //void deleteEmployeeDTO(Long id);
}
