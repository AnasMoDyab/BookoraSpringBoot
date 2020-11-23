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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

@DisplayName("RestPasswordServiceImp unit test")
@Tag("Development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class RestPasswordServiceImplTest {

    @Mock
    RestPasswordCodeRepository restPasswordCodeRepository;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    PasswordEncoder encoder;
    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    RestPasswordServiceImpl restPasswordService;

    @AfterEach
    void tearDown() {
        reset(restPasswordCodeRepository);
        reset(employeeRepository);
        reset(encoder);
        reset(authenticationManager);

    }

    @DisplayName("Update password with the previous password")
    @Test
    void updatePasswordWithPreviousPassword() throws Exception {
        //given
        Employee employee = new Employee();
        employee.setPassword("password");
        employee.setAbleTochangePassword(true);
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("email", "password");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);
        given(encoder.matches(any(), any())).willReturn(true);

        //when
        assertThatThrownBy(() -> {
            restPasswordService.updatePassword(updatePasswordDTO);
        })
                //then
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("you have used the old password");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(encoder).should(times(1)).matches(any(), any());
    }

    @DisplayName("Update password with the previous password")
    @Test
    void updatePassword() throws Exception {
        //given
        Employee employee = new Employee();
        employee.setPassword("password");
        employee.setAbleTochangePassword(true);
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("email", "password");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);
        given(encoder.matches(any(), any())).willReturn(false);
        given(encoder.encode(any())).willReturn("test");

        //when
        MessageDTO messageDTO = restPasswordService.updatePassword(updatePasswordDTO);

        //then
        assertThat(messageDTO.getMessage()).isEqualTo("Password successfully reset. You can now log in with the new credentials.");
        assertThat(employee.isAbleTochangePassword()).isEqualTo(false);
        assertThat(employee.getPassword()).isEqualTo("test");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(encoder).should(times(1)).matches(any(), any());
        then(encoder).should(times(1)).encode(any());
    }

    @DisplayName("Update password without permission")
    @Test
    void updatePasswordWithOutPermission() throws Exception {
        //given
        Employee employee = new Employee();
        employee.setPassword("password");
        employee.setAbleTochangePassword(false);
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("email", "password");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);

        //when
        assertThatThrownBy(() -> {
            restPasswordService.updatePassword(updatePasswordDTO);
        })
                //then
                .isInstanceOf(InvalidActionException.class)
                .hasMessage("You can't change the password");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
    }

    @DisplayName("Update password with non-existing employee")
    @Test
    void updatePasswordWithNonExistingEmployee() throws Exception {
        //given
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("email", "password");

        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(null);

        //when
        assertThatThrownBy(() -> {
            restPasswordService.updatePassword(updatePasswordDTO);
        })
                //then
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessage("Error: Email is invalid");
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
    }


    @DisplayName("Check a valid code")
    @Test
    void checkValidCode() {
        //given
        RestPasswordCode code = new RestPasswordCode();
        code.setExpiryDate(Timestamp.valueOf("2022-11-11 11:11:11"));

        given(restPasswordCodeRepository.findByConfirmationCode(any())).willReturn(code);

        //when
        boolean ok = restPasswordService.checkCode(any());

        //then
        assertThat(ok).isEqualTo(true);
        then(restPasswordCodeRepository).should(times(1)).findByConfirmationCode(any());
    }

    @DisplayName("Check a invalid code")
    @Test
    void checkInvalidCode() {
        //given
        given(restPasswordCodeRepository.findByConfirmationCode(any())).willReturn(null);

        //when
        boolean ok = restPasswordService.checkCode(any());

        //then
        assertThat(ok).isEqualTo(false);
        then(restPasswordCodeRepository).should(times(1)).findByConfirmationCode(any());
    }

    @DisplayName("Check a expired code")
    @Test
    void checkExpiredCode() {
        //given
        RestPasswordCode code = new RestPasswordCode();
        code.setExpiryDate(Timestamp.valueOf("2000-11-11 11:11:11"));

        given(restPasswordCodeRepository.findByConfirmationCode(any())).willReturn(code);

        //when
        boolean ok = restPasswordService.checkCode(any());

        //then
        assertThat(ok).isEqualTo(false);
        then(restPasswordCodeRepository).should(times(1)).findByConfirmationCode(any());
    }

}