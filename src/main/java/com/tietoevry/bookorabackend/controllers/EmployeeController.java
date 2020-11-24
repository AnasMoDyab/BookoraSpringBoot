package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.services.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping({"/{id}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployeeById(@PathVariable Long id) throws EmployeeNotFoundException {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployeeByEmail(@PathVariable String email) throws Exception {
        return employeeService.getEmployeeByEmail(email);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO createNewEmployee(@RequestBody @Valid SignUpDTO signUpDTO) throws Exception {
        return employeeService.createNewEmployee(signUpDTO);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    //todo Block user after attemping 3 times to login with wrong password(Brute-force attacks)
    public JwtDTO authenticateUser(@Valid @RequestBody LogInDTO logInDTO) throws Exception {
        return employeeService.logIn(logInDTO);
    }

    @PostMapping("/updateEmployee")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public MessageDTO UpdateEmployee(@RequestBody EmailDTO emailDTO) throws Exception {
        return employeeService.updateEmployee(emailDTO);
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeDTO(id);
    }

    // Receive the address and send an email
    @PostMapping({"/forgot-password"})
    public MessageDTO forgotUserPassword(@Valid @RequestBody ForgetPasswordDTO forgetPasswordDTO) throws EmployeeNotFoundException {
        return employeeService.sendForgetPasswordCode(forgetPasswordDTO);
    }

    //ReActivation Account
    @RequestMapping(value = "/reactive-account", method = RequestMethod.POST)
    public MessageDTO resetUserPassword(@RequestBody @Valid ReActiveEmailDTO reActiveEmailDTO) throws EmployeeNotFoundException {
        return employeeService.resendConfirmationToken(reActiveEmailDTO);
    }
}


