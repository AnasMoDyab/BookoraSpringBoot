package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;

public interface EmployeeService {
    //EmployeeListDTO getAllEmployees();

    //EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException;

    EmployeeDTO getEmployeeByEmail(String email) throws Exception;

    MessageDTO createNewEmployee(SignUpDTO signUpDTO) throws Exception;

    JwtDTO logIn(LogInDTO logInDTO) throws Exception;

    MessageDTO updateEmployee(EmailDTO emailDTO) throws Exception;


    MessageDTO sendForgetPasswordCode(ForgetPasswordDTO forgetPasswordDTO) throws EmployeeNotFoundException;

    MessageDTO resendConfirmationToken(ReActiveEmailDTO reActiveEmailDTO) throws EmployeeNotFoundException;


    //EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO);

    //void deleteEmployeeDTO(Long id);
}
