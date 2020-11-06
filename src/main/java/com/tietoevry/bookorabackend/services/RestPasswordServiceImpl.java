package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.RestPasswordCode;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordCodeRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RestPasswordServiceImpl implements RestPasswordService {
    private final RestPasswordCodeRepository restPasswordCodeRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public RestPasswordServiceImpl(RestPasswordCodeRepository restPasswordCodeRepository, EmployeeRepository employeeRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.restPasswordCodeRepository = restPasswordCodeRepository;
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public MessageDTO updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        System.out.println(updatePasswordDTO.email);


        Employee employee = employeeRepository.findByEmailIgnoreCase(updatePasswordDTO.getEmail());
        System.out.println(employee.getPassword());
        System.out.println(encoder.encode(updatePasswordDTO.getPassword()));
        if (employee == null) {
            return new MessageDTO("Error: Email is invalid");
        }
        else {

            if (employee.isAbleTochangePassword()) {
// Todo Employee shouldn't change to old password
              String oldPassword=  employee.getPassword();
              if(encoder.matches(updatePasswordDTO.getPassword(),oldPassword)){
                  System.out.println("Yoh can't change to old password");
                  return new MessageDTO("You have change to ");
              }
                System.out.println("success");

                employee.setPassword(encoder.encode(updatePasswordDTO.getPassword()));
                employee.setAbleTochangePassword(false);
                employeeRepository.save(employee);

                return new MessageDTO("Password successfully reset. You can now log in with the new credentials.");
            } else {
                return new MessageDTO("You can't change the password");
            }
        }



    }

    @Override
    public boolean checkCode(String codeConfirmationDto) {
        RestPasswordCode confirmation = restPasswordCodeRepository.findByConfirmationCode(codeConfirmationDto);
        if (confirmation != null) {

            if (confirmation.getExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
                return false;
            } else {
                return true;
            }

        } else {
            return false;
        }
    }
}








