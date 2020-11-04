package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.*;

public interface EmployeeService {
    EmployeeListDTO getAllEmployees();

    EmployeeDTO getEmployeeById(Long id);

    MessageDTO createNewEmployee(SignUpDTO signUpDTO);

    MessageDTO resendConfirmationToken(ReActiveEmailDTO reActiveEmailDTO);

    JwtDTO logIn(LogInDTO logInDTO);

    MessageDTO sendForgetPasswordCode(ForgetPasswordDTO forgetPasswordDTO);

    EmployeeDTO saveEmployeeByDTO(Long id, EmployeeDTO employeeDTO);

    //EmployeeDTO patchEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployeeDTO(Long id);
}
