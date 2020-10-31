package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.model.CodeConfirmationDto;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.UpdatePasswordDTO;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.RestPassword;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RestPasswordServiceImpl implements RestPasswordService {
    private final RestPasswordRepository restPasswordRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;

    public RestPasswordServiceImpl(RestPasswordRepository restPasswordRepository, EmployeeRepository employeeRepository, PasswordEncoder encoder) {
        this.restPasswordRepository = restPasswordRepository;
        this.employeeRepository = employeeRepository;
        this.encoder = encoder;
    }

/*    @Override
    public ModelAndView checkToken(String confirmationToken *//*, HttpServletResponse response*//*) {
        RestPassword token = restPasswordRepository.findByRestPasswordCode(confirmationToken);
        //System.out.println(response);
       // HttpHeaders headers = new HttpHeaders();
        if (token != null) {


            if (token.getExpiryDate().before(new Timestamp(System.currentTimeMillis()))) {
              // return new MessageDTO("Error: token has expired!");/// TODO: redirect to password page
               // headers.add("Location", "localhost:3000/NewPassword");
              //  return new ResponseEntity(headers, HttpStatus.FOUND);
                return new ModelAndView("redirect:http://localhost:3000");
            }
            Employee employee = employeeRepository.findByEmailIgnoreCase(token.getEmployee().getEmail());
            employee.setAbleToChangePassword(true);
            employeeRepository.save(employee);
           // headers.add("Location", "localhost:3000/NewPassword");
          //  System.out.println(new ResponseEntity(headers, HttpStatus.FOUND));
         //   return new ResponseEntity(headers, HttpStatus.FOUND);
        //   return new MessageDTO("We will connect this link to password page");
            return new ModelAndView("redirect:http://localhost:3000");
        }
      else{
                //return new MessageDTO("Error: The link is invalid or broken!");
         //   headers.add("Location", "localhost:3000/NewPassword");
         //   return new ResponseEntity(headers, HttpStatus.FOUND);
            return new ModelAndView("redirect:http://localhost:3000");
            }
    }*/

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
    public boolean checkCode(CodeConfirmationDto codeConfirmationDto) {
        RestPassword confirmation = restPasswordRepository.findByConfirmationCode(codeConfirmationDto.getConfirmationCode());
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








