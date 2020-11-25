package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;

public interface EmployeeService {
    //EmployeeListDTO getAllEmployees();

    //EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException;

    MessageDTO createNewEmployee(SignUpDTO signUpDTO) throws Exception;

    MessageDTO resendConfirmationToken(ReActiveEmailDTO reActiveEmailDTO) throws EmployeeNotFoundException;

    JwtDTO logIn(LogInDTO logInDTO) throws Exception;

    MessageDTO sendForgetPasswordCode(ForgetPasswordDTO forgetPasswordDTO) throws EmployeeNotFoundException;

    MessageDTO updateEmployee(EmailDTO emailDTO) throws Exception;

    EmployeeDTO getEmployeeByEmail(String email) throws Exception;

    //EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployeeDTO(Long id);
}
