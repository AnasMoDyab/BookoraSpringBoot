package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.RestPasswordCode;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordCodeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
    public MessageDTO updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        System.out.println(updatePasswordDTO.email);


        Employee employee = employeeRepository.findByEmailIgnoreCase(updatePasswordDTO.getEmail());
        if (employee == null) {
            return new MessageDTO("Error: Email is invalid");
        }
        else {

            employee.setPassword(encoder.encode(updatePasswordDTO.getPassword()));
            employeeRepository.save(employee);
            return new MessageDTO("Password successfully reset. You can now log in with the new credentials.");
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








