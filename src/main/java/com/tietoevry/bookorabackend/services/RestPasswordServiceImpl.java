package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.exception.InvalidActionException;
import com.tietoevry.bookorabackend.exception.InvalidInputException;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.RestPasswordCode;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordCodeRepository;
import org.springframework.security.authentication.AuthenticationManager;
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
    public MessageDTO updatePassword(UpdatePasswordDTO updatePasswordDTO) throws Exception {
        Employee employee = employeeRepository.findByEmailIgnoreCase(updatePasswordDTO.getEmail());

        if (employee == null) {
            throw new EmployeeNotFoundException("Error: Email is invalid");

        } else {

            if (employee.isAbleTochangePassword()) {
                String oldPassword = employee.getPassword();

                if (encoder.matches(updatePasswordDTO.getPassword(), oldPassword)) {
                    throw new InvalidInputException("you have used the old password");
                }

                employee.setPassword(encoder.encode(updatePasswordDTO.getPassword()));
                employee.setAbleTochangePassword(false);
                employeeRepository.save(employee);

                return new MessageDTO("Password successfully reset. You can now log in with the new credentials.");

            } else {
                throw new InvalidActionException("You can't change the password");
            }
        }


    }

    @Override
    public boolean checkCode(String codeConfirmationDto) {
        RestPasswordCode confirmation = restPasswordCodeRepository.findByConfirmationCode(codeConfirmationDto);

        if (confirmation != null) {
            return !confirmation.getExpiryDate().before(new Timestamp(System.currentTimeMillis()));

        } else {
            return false;
        }
    }
}
