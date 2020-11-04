package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.services.ConfirmationTokenService;
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
    private final ConfirmationTokenService confirmationTokenService;
    private final EmployeeRepository employeeRepository;


    public EmployeeController(EmployeeService employeeService, ConfirmationTokenService confirmationTokenService, EmployeeRepository employeeRepository
    ) {
        this.employeeService = employeeService;
        this.confirmationTokenService = confirmationTokenService;
        this.employeeRepository = employeeRepository;

    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeListDTO getEmployeeList() {
        return employeeService.getAllEmployees();
    }

    @GetMapping({"/{id}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO createNewEmployee(@RequestBody @Valid SignUpDTO signUpDTO) {
        return employeeService.createNewEmployee(signUpDTO);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public JwtDTO authenticateUser(@Valid @RequestBody LogInDTO logInDTO) {
        return employeeService.logIn(logInDTO);
    }


    @PutMapping({"/{id}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.saveEmployeeByDTO(id, employeeDTO);
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeDTO(id);
    }


    // Receive the address and send an email
    @PostMapping({"/forgot-password"})
    public MessageDTO forgotUserPassword(@Valid @RequestBody ForgetPasswordDTO forgetPasswordDTO) {

        return employeeService.sendForgetPasswordCode(forgetPasswordDTO);


    }
    //ReActivation Account
    @RequestMapping(value = "/reactive-account", method = RequestMethod.POST)
    public MessageDTO resetUserPassword(@RequestBody @Valid ReActiveEmailDTO reActiveEmailDTO) {
        return  employeeService.resendConfirmationToken(reActiveEmailDTO);
    }
}


