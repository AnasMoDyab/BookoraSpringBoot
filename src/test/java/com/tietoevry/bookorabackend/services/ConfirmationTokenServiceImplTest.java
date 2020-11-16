package com.tietoevry.bookorabackend.services;

import com.tietoevry.bookorabackend.model.ConfirmationToken;
import com.tietoevry.bookorabackend.model.Employee;
import com.tietoevry.bookorabackend.repositories.ConfirmationTokenRepository;
import com.tietoevry.bookorabackend.repositories.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;

@DisplayName("ConfirmationTokenServiceImp unit test")
@Tag("Development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class ConfirmationTokenServiceImplTest {

    @Mock
    ConfirmationTokenRepository confirmationTokenRepository;
    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    ConfirmationTokenServiceImpl confirmationTokenService;

    ConfirmationToken confirmationToken;

    @BeforeEach
    void setUp() {


    }

    @AfterEach
    void tearDown() {
        reset(confirmationTokenRepository);
        reset(employeeRepository);
    }

    @DisplayName("Token is null")
    @Test
    void checkNonExistedToken() {
        //given
        given(confirmationTokenRepository.findByConfirmationToken(any())).willReturn(confirmationToken);

        //when
        String message = confirmationTokenService.checkToken("test");

        //then
        assertThat(message).isEqualTo("linkInvalid.html");
        then(confirmationTokenRepository).should(times(1)).findByConfirmationToken(any());
    }

    @DisplayName("Token is expired")
    @Test
    void checkExpiredToken() {
        //given
        confirmationToken = new ConfirmationToken(null,null,null,null, Timestamp.valueOf("2020-11-11 11:11:11"));
        given(confirmationTokenRepository.findByConfirmationToken(any())).willReturn(confirmationToken);

        //when
        String message = confirmationTokenService.checkToken("test");

        //then
        assertThat(message).isEqualTo("ExpiredToken.html");
        then(confirmationTokenRepository).should(times(1)).findByConfirmationToken(any());
    }

    @DisplayName("Token is valid")
    @Test
    void checkValidToken() {
        //given
        confirmationToken = new ConfirmationToken(null,null,null,null, Timestamp.valueOf("2022-11-11 11:11:11"));
        Employee employee = new Employee();
        employee.setEnabled(false);
        given(confirmationTokenRepository.findByConfirmationToken(any())).willReturn(confirmationToken);
        given(employeeRepository.findByEmailIgnoreCase(any())).willReturn(employee);

        //when
        String message = confirmationTokenService.checkToken("test");

        //then
        assertThat(message).isEqualTo("confirm.html");
        then(confirmationTokenRepository).should(times(1)).findByConfirmationToken(any());
        then(employeeRepository).should(times(1)).findByEmailIgnoreCase(any());
        then(employeeRepository).should(times(1)).save(any());
    }
}