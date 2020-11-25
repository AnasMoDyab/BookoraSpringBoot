package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.services.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * A rest controller that provides API for managing employees.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Employee", description = "Employee API")
@RestController
@RequestMapping(EmployeeController.BASE_URL)
public class EmployeeController {

    public static final String BASE_URL = "/api/v1/employees";
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService
    ) {
        this.employeeService = employeeService;
    }
/*
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeListDTO getEmployeeList() {
        return employeeService.getAllEmployees();
    }*/

/*    @GetMapping({"/{id}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(id);
    }*/

    /**
     * Provides information about a selected employee.
     *
     * @param email A string of email
     * @return An EmployeeDTO that contains information of the employee including ID, first name, last name, email,
     * password and role
     * @throws Exception EmployeeNotFoundException if employee is not found
     */
    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployeeByEmail(@PathVariable String email) throws Exception {
        return employeeService.getEmployeeByEmail(email);
    }

    /**
     * Create a new employee.
     *
     * @param signUpDTO A SignUpDTO that contains information about employee, including last name, first name, email,
     *                  password and set of roles
     * @return A MessageDTO that contains message string about the creation of a new employee
     * @throws Exception InvalidDomainException if the email domain is not valid
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO createNewEmployee(@RequestBody @Valid SignUpDTO signUpDTO) throws Exception {
        return employeeService.createNewEmployee(signUpDTO);
    }

    /**
     * Signs in a employee.
     * 
     * @param logInDTO A LoginDTO that contains email and password of the employee
     * @return A JwtDTO that contains information including token string, token type, token ID, email of the employee 
     * and list of roles of the employee
     * @throws Exception EmployeeNotFoundException if the employee is not found
     * @throws Exception EmployeeNotActivatedException if the email account is not activated
     */
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    //todo Block user after attemping 3 times to login with wrong password(Brute-force attacks)
    public JwtDTO authenticateUser(@Valid @RequestBody LogInDTO logInDTO) throws Exception {
        return employeeService.logIn(logInDTO);
    }

    /**
     * Updates an employee.
     * 
     * @param emailDTO A E
     * @return
     * @throws Exception
     */
    @PostMapping("/updateEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO UpdateEmployee(@RequestBody EmailDTO emailDTO) throws Exception {
        return employeeService.updateEmployee(emailDTO);
    }

   /* @DeleteMapping({"/{id}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeDTO(id);
    }*/

    // Receive the address and send an email
    @PostMapping({"/forgot-password"})
    public MessageDTO forgotUserPassword(@RequestBody @Valid EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException {
        return employeeService.sendForgetPasswordCode(employeeEmailDTO);
    }

    //ReActivation Account
    @RequestMapping(value = "/reactive-account", method = RequestMethod.POST)
    public MessageDTO resetUserPassword(@RequestBody @Valid EmployeeEmailDTO employeeEmailDTO) throws EmployeeNotFoundException {
        return employeeService.resendConfirmationToken(employeeEmailDTO);
    }
}


