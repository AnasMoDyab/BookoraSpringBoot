package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.RestPasswordToken;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordTokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

@Service
public class RestPasswordTokenServiceImpl implements  RestPasswordTokenService{
    private final RestPasswordTokenRepository restPasswordTokenRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

    public RestPasswordTokenServiceImpl(RestPasswordTokenRepository restPasswordTokenRepository, EmployeeRepository employeeRepository, PasswordEncoder encoder) {
        this.restPasswordTokenRepository = restPasswordTokenRepository;
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

    @Override
    public MessageDTO checkToken(String confirmationToken /*, HttpServletResponse response*/) {
        RestPasswordToken token =restPasswordTokenRepository.findByRestPasswordToken (confirmationToken);
        //System.out.println(response);
       // HttpHeaders headers = new HttpHeaders();
        if (token != null) {


            if (token.getExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
               return new MessageDTO("Error: token has expired!");/// TODO: redirect to password page
               // headers.add("Location", "localhost:3000/NewPassword");
              //  return new ResponseEntity(headers, HttpStatus.FOUND);
            }
            Employee employee = employeeRepository.findByEmailIgnoreCase(token.getEmployee().getEmail());
            employee.setAbleToChangePassword(true);
            employeeRepository.save(employee);
           // headers.add("Location", "localhost:3000/NewPassword");
          //  System.out.println(new ResponseEntity(headers, HttpStatus.FOUND));
         //   return new ResponseEntity(headers, HttpStatus.FOUND);
           return new MessageDTO("We will connect this link to password page");
        }
      else{
                return new MessageDTO("Error: The link is invalid or broken!");
         //   headers.add("Location", "localhost:3000/NewPassword");
         //   return new ResponseEntity(headers, HttpStatus.FOUND);
            }
    }

    @Override
    public MessageDTO updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        System.out.println(updatePasswordDTO.email);
        if(updatePasswordDTO.getEmail() != null){
            // Use email to find user
           Employee tokenUser = employeeRepository.findByEmailIgnoreCase(updatePasswordDTO.getEmail());
            tokenUser.setPassword(encoder.encode(updatePasswordDTO.getPassword()));
            System.out.println(updatePasswordDTO.getPassword());

            employeeRepository.save(tokenUser);
            return  new MessageDTO( "Password successfully reset. You can now log in with the new credentials.");

        } else {
            return  new MessageDTO(   "The link is invalid or broken!");

    }

        }

    }





