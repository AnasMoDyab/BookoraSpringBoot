package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.model.ConfirmationToken;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.repositories.ConfirmationTokenRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * {@inheritDoc}
 */
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmployeeRepository employeeRepository;


    public ConfirmationTokenServiceImpl(ConfirmationTokenRepository confirmationTokenRepository, EmployeeRepository employeeRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.employeeRepository = employeeRepository;

    }

    @Override
    public String checkToken(String confirmationToken) {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {

            if (token.getExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
                return "ExpiredToken.html";
            }

            Employee employee = employeeRepository.findByEmailIgnoreCase(token.getEmployee().getEmail());
            employee.setEnabled(true);
            employeeRepository.save(employee);
            return "confirm.html";

        } else {
            return "linkInvalid.html";
        }
    }
}
