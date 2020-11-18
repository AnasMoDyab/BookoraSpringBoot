package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;

public interface EmployeeService {
    EmployeeListDTO getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    MessageDTO createNewEmployee(SignUpDTO signUpDTO) throws Exception;

    MessageDTO resendConfirmationToken(ReActiveEmailDTO reActiveEmailDTO);

    JwtDTO logIn(LogInDTO logInDTO) throws EmployeeNotFoundException;

    MessageDTO sendForgetPasswordCode(ForgetPasswordDTO forgetPasswordDTO);

    MessageDTO updateEmployee(EmailDTO emailDTO);
    EmployeeDTO getEmployeeByEmail( String email);

    //EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployeeDTO(Long id);
}
