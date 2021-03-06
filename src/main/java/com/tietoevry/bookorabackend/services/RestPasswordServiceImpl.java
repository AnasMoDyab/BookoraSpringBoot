package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.exception.InvalidActionException;
import com.tietoevry.bookorabackend.exception.InvalidInputException;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.RestPasswordCode;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordCodeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * {@inheritDoc}
 */
@Service
public class RestPasswordServiceImpl implements RestPasswordService {
    private final RestPasswordCodeRepository restPasswordCodeRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

    public RestPasswordServiceImpl(RestPasswordCodeRepository restPasswordCodeRepository, EmployeeRepository employeeRepository, PasswordEncoder encoder) {
        this.restPasswordCodeRepository = restPasswordCodeRepository;
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

    @Override
    public MessageDTO updatePassword(LogInDTO logInDTO) throws Exception {
        Employee employee = employeeRepository.findByEmailIgnoreCase(logInDTO.getEmail());

        if (employee == null) {
            throw new EmployeeNotFoundException("Error: Email is invalid");

        } else {

            if (employee.isAbleToChangePassword()) {
                String oldPassword = employee.getPassword();

                if (encoder.matches(logInDTO.getPassword(), oldPassword)) {
                    throw new InvalidInputException("you have used the old password");
                }

                employee.setPassword(encoder.encode(logInDTO.getPassword()));
                employee.setAbleToChangePassword(false);
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
