package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.EmployeeMapper;
import com.tietoevry.bookorabackend.api.v1.mapper.SignUpMapper;
import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import com.tietoevry.bookorabackend.exception.*;
import com.tietoevry.bookorabackend.model.*;
import com.tietoevry.bookorabackend.repositories.ConfirmationTokenRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordCodeRepository;
import com.tietoevry.bookorabackend.repositories.RoleRepository;
import com.tietoevry.bookorabackend.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImp implements EmployeeService {

    private final AuthenticationManager authenticationManager;
    private final EmployeeMapper employeeMapper;
    private final SignUpMapper signUpMapper;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final EmployeeRepository employeeRepository;
    private final EmailSenderService emailSenderService;
    private final RoleRepository roleRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final RestPasswordCodeRepository restPasswordCodeRepository;
    @Value("${validDomain}")
    private String validDomain;
    @Value("${confirmationTokenValidMinute}")
    private int validMinute;
    @Value("${restPasswordCodeValidMinute}")
    private int restPasswordCodeValidMinute;

    public EmployeeServiceImp(AuthenticationManager authenticationManager, EmployeeMapper employeeMapper, SignUpMapper signUpMapper, PasswordEncoder encoder, JwtUtils jwtUtils, EmployeeRepository employeeRepository, EmailSenderService emailSenderService, RoleRepository roleRepository, ConfirmationTokenRepository confirmationTokenRepository, RestPasswordCodeRepository restPasswordCodeRepository) {
        this.authenticationManager = authenticationManager;
        this.employeeMapper = employeeMapper;
        this.signUpMapper = signUpMapper;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.employeeRepository = employeeRepository;
        this.emailSenderService = emailSenderService;
        this.roleRepository = roleRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.restPasswordCodeRepository = restPasswordCodeRepository;
    }

    @Override
    public EmployeeListDTO getAllEmployees() {
        List<EmployeeDTO> employeeDTOList = employeeRepository.findAll().stream().map(employee -> {
            EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);
            employeeDTO.setEmployeeUrl(getEmployeeUrl(employee.getId()));
            return employeeDTO;
        }).collect(Collectors.toList());
        return new EmployeeListDTO(employeeDTOList);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) throws EmployeeNotFoundException {
        return employeeRepository
                .findById(id)
                .map(employeeMapper::employeeToEmployeeDTO)
                .map(employeeDTO -> {
                    employeeDTO.setEmployeeUrl(getEmployeeUrl(id));
                    return employeeDTO;
                })
                .orElseThrow(() -> new EmployeeNotFoundException("Employee is not found"));
    }


    @Override
    public MessageDTO createNewEmployee(SignUpDTO signUpDTO) throws Exception {

        //Validate domain
        String email = signUpDTO.getEmail();
        String domain = email.substring(email.indexOf("@") + 1);
        if (!domain.equals(validDomain))
            //return new MessageDTO("Error: Email domain is not valid!");
            throw new InvalidDomainException("Error: Email domain is not valid!");

        if (existedByEmail(signUpDTO.getEmail())) {
            throw new UserExistedException("Error: Email is already in use!");
        } else {
            //Mapping signDTO to employee
            Employee employee = signUpMapper.signUpDTOtoEmployee(signUpDTO);

            //encode password
            employee.setPassword(encoder.encode(signUpDTO.getPassword()));

            //Check if the roles exist, if exist add to employee
            Set<String> strRoles = signUpDTO.getRoles();
            Set<Role> roles = new HashSet<>();
            if (strRoles == null) {
                Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                        .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                for (String role : strRoles) {
                    if ("admin".equals(role)) {
                        Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);
                    }
                }
            }

            //Always add employee as a user
            Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
            roles.add(userRole);

            employee.setRoles(roles);
            Employee savedEmployee = employeeRepository.save(employee);

            ConfirmationToken confirmationToken = new ConfirmationToken(savedEmployee);
            confirmationToken.setExpiryDate(calculateExpiryDate(1)); //in unit of minute
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(employee.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("oslomet8@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            return new MessageDTO("User registered successfully!");
        }

    }

    @Override
    public MessageDTO resendConfirmationToken(ReActiveEmailDTO reActiveEmailDTO) throws EmployeeNotFoundException {
        String email = reActiveEmailDTO.getEmail();
        String domain = email.substring(email.indexOf("@") + 1);

        if (!domain.equals(validDomain))
            throw new EmployeeNotFoundException("Error: Email domain is not valid!");


        Employee employee = employeeRepository.findByEmailIgnoreCase(reActiveEmailDTO.getEmail());

        ConfirmationToken confirmationToken = new ConfirmationToken(employee);
        confirmationToken.setExpiryDate(calculateExpiryDate(validMinute)); //in unit of minute
        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(employee.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("oslomet8@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);

        return new MessageDTO("Cods is sent, Check your email!");

    }

    @Override
    public JwtDTO logIn(LogInDTO logInDTO) throws Exception {
        String email = logInDTO.getEmail();

        Employee employee = employeeRepository.findByEmailIgnoreCase(email);

        if (employee == null) throw new EmployeeNotFoundException("Incorrect email or password");

        if (employee.isEnabled()) {

            //Authenticate and return an Authentication object that can be used to find user information
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(logInDTO.getEmail(), logInDTO.getPassword()));

            //Update SecurityContext using Authentication object
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Generate JWT
            String jwt = jwtUtils.generateJwtToken(authentication);

            //Get UserDetails from Authentication object
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); //authentication.getPrincipal() return object of org.springframework.security.core.userdetails.User

            //Get roles from UserDetails
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return new JwtDTO(jwt,
                    userDetails.getId(),
                    userDetails.getEmail(),
                    roles);
        }

        throw new EmployeeNotActivatedException("Email is not activated");
    }

    @Override
    public MessageDTO sendForgetPasswordCode(ForgetPasswordDTO forgetPasswordDTO) throws EmployeeNotFoundException {
        Employee existingEmployee = employeeRepository.findByEmailIgnoreCase(forgetPasswordDTO.getEmail());

        if (existingEmployee != null) {
            // Create token
            RestPasswordCode restPasswordCode = new RestPasswordCode(existingEmployee);
            restPasswordCode.setExpiryDate(calculateExpiryDate(restPasswordCodeValidMinute));

            // Save it
            existingEmployee.setAbleTochangePassword(true);
            restPasswordCodeRepository.save(restPasswordCode);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingEmployee.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("oslomet8@gmail.com");
            mailMessage.setText("To complete the password reset process, please use this code: "
                    + restPasswordCode.getConfirmationCode());

            // Send the email
            emailSenderService.sendEmail(mailMessage);

            return new MessageDTO("Request to reset password received. Check your inbox.");

        } else {

            throw new EmployeeNotFoundException("This email address does not exist!");
        }


    }

    @Override
    public MessageDTO updateEmployee(EmailDTO emailDTO) throws Exception {

        Employee employee = employeeRepository.findByEmailIgnoreCase(emailDTO.getEmail());


        if (employee != null) {
            employee.setRoles(null);

            Role user = new Role(1L, RoleEnum.ROLE_USER);
            Role admin = new Role(2L, RoleEnum.ROLE_ADMIN);

            HashSet<Role> Roles = new HashSet<>();

            for (String role : emailDTO.getRole()) {

                if (role.equals("admin")) {
                    Roles.add(admin);
                }

                if (role.equals("user")) {
                    Roles.add(user);
                }

            }
            employee.setRoles(Roles);

            employeeRepository.save(employee);

            return new MessageDTO("Updated success");
        }
        throw new EmployeeNotFoundException("Updated failed");
    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        String domain = email.substring(email.indexOf("@") + 1);

        if (!domain.equals(validDomain)) {
            return null;
        }

        Employee employee = employeeRepository.findByEmailIgnoreCase(email);

        if (employee != null) {
            EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);
            Set<String> roles = new HashSet<>();

            for (Role role : employee.getRoles()) {
                roles.add(role.getName().toString());
            }

            employeeDTO.setRole(roles);
            return employeeDTO;

        }

        return null;
    }

    @Override
    public void deleteEmployeeDTO(Long id) {
        employeeRepository.deleteById(id);
    }

    private String getEmployeeUrl(Long id) {
        return EmployeeController.BASE_URL + "/" + id;
    }

    private boolean existedByEmail(String email) {
        Employee employee = employeeRepository.findByEmailIgnoreCase(email);
        return employee != null;
    }

    //Setting expiryTime in unit of minute
    private Timestamp calculateExpiryDate(int expiryTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiryTime);
        return new Timestamp(calendar.getTime().getTime());
    }
}
