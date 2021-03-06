package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.api.v1.mapper.EmployeeMapper;
import com.tietoevry.bookorabackend.api.v1.mapper.SignUpMapper;
import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.exception.EmployeeNotActivatedException;
import com.tietoevry.bookorabackend.exception.EmployeeNotFoundException;
import com.tietoevry.bookorabackend.exception.InvalidDomainException;
import com.tietoevry.bookorabackend.exception.UserExistedException;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.model.Role;
import com.tietoevry.bookorabackend.repositories.ConfirmationTokenRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import com.tietoevry.bookorabackend.repositories.RestPasswordCodeRepository;
import com.tietoevry.bookorabackend.repositories.RoleRepository;
import com.tietoevry.bookorabackend.security.jwt.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.tietoevry.bookorabackend.model.RoleEnum.ROLE_ADMIN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

@DisplayName("EmployeeServiceImp unit test")
@Tag("Development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImpTest {

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    EmployeeMapper employeeMapper;
    @Mock
    SignUpMapper signUpMapper;
    @Mock
    PasswordEncoder encoder;
    @Mock
    JwtUtils jwtUtils;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmailSenderService emailSenderService;
    @Mock
    RoleRepository roleRepository;
    @Mock
    ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    RestPasswordCodeRepository restPasswordCodeRepository;

    @InjectMocks
    EmployeeServiceImp employeeServiceImp;

    @AfterEach
    void tearDown() {
        reset(authenticationManager);
        reset(employeeMapper);
        reset(signUpMapper);
        reset(encoder);
        reset(jwtUtils);
        reset(employeeRepository);
        reset(emailSenderService);
        reset(roleRepository);
        reset(confirmationTokenRepository);
        reset(restPasswordCodeRepository);
    }

    @DisplayName("Create new employee with invalid domain")
    @Test
    void createNewEmployeeWithInvalidDomain() throws Exception {
        //given
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail("abc@invalid.com");

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.createNewEmployee(signUpDTO);
        })
                //then
                .isInstanceOf(InvalidDomainException.class)
                .hasMessage("Error: Email domain is not valid!");

    }

    @DisplayName("Create new employee with already existing email")
    @Test
    void createNewEmployeeWithAlreadyExistingEmail() throws Exception {
        //given
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail("abc@tietoevry.com");

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(new Employee());

        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.createNewEmployee(signUpDTO);
        })
                //then
                .isInstanceOf(UserExistedException.class)
                .hasMessage("Error: Email is already in use!");

        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
    }

    @DisplayName("Create new employee with default role")
    @Test
    void createNewEmployeeWithDefaultRole() throws Exception {
        //given
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail("abc@tietoevry.com");
        Employee employee = new Employee();

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(null);
        given(signUpMapper.signUpDTOtoEmployee(any())).willReturn(new Employee());
        given(encoder.encode(any())).willReturn("encodedPassword");
        given(roleRepository.findByName(any())).willReturn(Optional.of(new Role()));
        given(employeeRepository.save(any())).willReturn(employee);

        //when
        MessageDTO message = employeeServiceImp.createNewEmployee(signUpDTO);

        //then
        assertThat(message.getMessage()).isEqualTo("User registered successfully!");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(signUpMapper).should(times(1)).signUpDTOtoEmployee(any());
        then(encoder).should(times(1)).encode(any());
        then(roleRepository).should(times(2)).findByName(any());
        then(employeeRepository).should(times(1)).save(any());
    }

    @DisplayName("Create new employee with admin role")
    @Test
    void createNewEmployeeWithAdminRole() throws Exception {
        //given
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setEmail("abc@tietoevry.com");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        signUpDTO.setRoles(roles);
        Employee employee = new Employee();

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(null);
        given(signUpMapper.signUpDTOtoEmployee(any())).willReturn(new Employee());
        given(encoder.encode(any())).willReturn("encodedPassword");
        given(roleRepository.findByName(any())).willReturn(Optional.of(new Role()));
        given(employeeRepository.save(any())).willReturn(employee);

        //when
        MessageDTO message = employeeServiceImp.createNewEmployee(signUpDTO);

        //then
        assertThat(message.getMessage()).isEqualTo("User registered successfully!");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(signUpMapper).should(times(1)).signUpDTOtoEmployee(any());
        then(encoder).should(times(1)).encode(any());
        then(roleRepository).should(times(2)).findByName(any());
        then(employeeRepository).should(times(1)).save(any());
    }

    @DisplayName("Request resend of confirmation token by invalid email domain")
    @Test
    void resendConfirmationTokenWithInvalidDomain() throws EmployeeNotFoundException {
        //given
        EmployeeEmailDTO employeeEmailDTO = new EmployeeEmailDTO();
        employeeEmailDTO.setEmail("abc@invalid.com");

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.resendConfirmationToken(employeeEmailDTO);
        })
                //then
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Error: Email domain is not valid!");
    }

    @DisplayName("Request resend of confirmation token by valid email domain")
    @Test
    void resendConfirmationToken() throws EmployeeNotFoundException {
        //given
        EmployeeEmailDTO employeeEmailDTO = new EmployeeEmailDTO();
        employeeEmailDTO.setEmail("abc@tietoevry.com");

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(new Employee());

        //when
        MessageDTO message = employeeServiceImp.resendConfirmationToken(employeeEmailDTO);

        //then
        assertThat(message.getMessage()).isEqualTo("Cods is sent, Check your email!");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(confirmationTokenRepository).should(times(1)).save(any());
        then(emailSenderService).should(times(1)).sendEmail(any());
    }

    @DisplayName("Log in with an activated account")
    @Test
    void logIn() throws Exception {
        //given
        LogInDTO logInDTO = new LogInDTO("email", "password");
        Employee employee = new Employee();
        employee.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ROLE_ADMIN));
        employee.setRoles(roles);
        employee.setId(1L);
        employee.setEmail("email");
        UserDetailsImpl userDetails = UserDetailsImpl.build(employee);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, "password");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);
        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(jwtUtils.generateJwtToken(any())).willReturn("abc123");

        //when
        JwtDTO jwtDTO = employeeServiceImp.logIn(logInDTO);

        //then
        assertThat(jwtDTO.getToken()).isEqualTo("abc123");
        assertThat(jwtDTO.getType()).isEqualTo("Bearer");
        assertThat(jwtDTO.getId()).isEqualTo(1);
        assertThat(jwtDTO.getEmail()).isEqualTo("email");
        assertThat(jwtDTO.getRoles().get(0)).isEqualTo("ROLE_ADMIN");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(authenticationManager).should(times(1)).authenticate(any());
        then(jwtUtils).should(times(1)).generateJwtToken(any());
    }

    @DisplayName("Log in with an non-activated account")
    @Test
    void logInWithNonActivatedAccount() throws Exception {
        //given
        LogInDTO logInDTO = new LogInDTO("email", "password");
        Employee employee = new Employee();
        employee.setEnabled(false);

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);

        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.logIn(logInDTO);
        })
                //then
                .isInstanceOf(EmployeeNotActivatedException.class)
                .hasMessage("Email is not activated");
    }

    @DisplayName("Request reset password code with valid email")
    @Test
    void sendForgetPasswordCode() throws EmployeeNotFoundException {
        //given
        EmployeeEmailDTO employeeEmailDTO = new EmployeeEmailDTO("email");
        Employee employee = new Employee();
        employee.setEmail("email");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);

        //when
        MessageDTO messageDTO = employeeServiceImp.sendForgetPasswordCode(employeeEmailDTO);

        //then
        assertThat(messageDTO.getMessage()).isEqualTo("Request to reset password received. Check your inbox.");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(restPasswordCodeRepository).should(times(1)).save(any());
        then(emailSenderService).should(times(1)).sendEmail(any());
    }

    @DisplayName("Request reset password code with invalid email")
    @Test
    void sendForgetPasswordCodeWithInvalidEmail() throws EmployeeNotFoundException {
        //given
        EmployeeEmailDTO employeeEmailDTO = new EmployeeEmailDTO("email");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(null);

        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.sendForgetPasswordCode(employeeEmailDTO);
        })
                //then
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("This email address does not exist!");
    }

    @DisplayName("Update an employee")
    @Test
    void updateEmployee() throws Exception {
        //given
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        EmailDTO emailDTO = new EmailDTO("email", roles);

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(new Employee());

        //when
        MessageDTO messageDTO = employeeServiceImp.updateEmployee(emailDTO);

        //then
        assertThat(messageDTO.getMessage()).isEqualTo("Updated success");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(employeeRepository).should(times(1)).save(any());
    }

    @DisplayName("Update an employee that does not exist")
    @Test
    void updateAnNonExistingEmployee() throws Exception {
        //given
        EmailDTO emailDTO = new EmailDTO();

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(null);


        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.updateEmployee(emailDTO);
        })
                //then
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Updated failed");
    }

    @DisplayName("Get an employee by invalid domain")
    @Test
    void getEmployeeValidDomain() throws Exception {
        //given
        String email = "abc@tietoevry.com";
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ROLE_ADMIN));
        Employee employee = new Employee();
        employee.setRoles(roles);

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);
        given(employeeMapper.employeeToEmployeeDTO(any())).willReturn(new EmployeeDTO());

        //when
        EmployeeDTO employeeDTO = employeeServiceImp.getEmployeeByEmail(email);

        //then
        assertThat(employeeDTO.getRole().toString()).isEqualTo("[ROLE_ADMIN]");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(employeeMapper).should(times(1)).employeeToEmployeeDTO(any());
    }

    @DisplayName("Get an employee by invalid domain")
    @Test
    void getEmployeeByInvalidDomain() throws Exception {
        //given
        String email = "abc@tietoevrya.com";

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.getEmployeeByEmail(email);
        })
                //then
                .isInstanceOf(InvalidDomainException.class);
    }

    @DisplayName("Get an employee by non-existing domain")
    @Test
    void getNonExistingEmployeeByEmail() throws Exception {
        //given
        String email = "abc@tietoevry.com";

        //setting the @Value in the class without bringing in the application properties
        ReflectionTestUtils.setField(employeeServiceImp, "validDomains", "tietoevry.com,evry.com");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(null);

        //when
        assertThatThrownBy(() -> {
            employeeServiceImp.getEmployeeByEmail(email);
        })
                //then
                .isInstanceOf(EmployeeNotFoundException.class);
    }

}