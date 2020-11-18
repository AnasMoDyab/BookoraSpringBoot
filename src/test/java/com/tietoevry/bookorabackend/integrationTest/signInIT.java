package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.JwtDTO;
import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.SignUpDTO;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
public class signInIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @DisplayName("Sign in with valid input")
    @Test
    void signIn() {
        //given
        LogInDTO logInDTO = new LogInDTO("root@tietoevry.com", "123456aB@");

        //when
        ResponseEntity<JwtDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                        , logInDTO, JwtDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getType()).isEqualTo("Bearer");
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getEmail()).isEqualTo("root@tietoevry.com");
        assertThat(response.getBody().getRoles().contains("ROLE_USER")).isEqualTo(true);
        assertThat(response.getBody().getRoles().contains("ROLE_ADMIN")).isEqualTo(true);
    }

    @DisplayName("Sign in with incorrect password")
    @Test
    void signInWithWrongPassword() {
        //given
        LogInDTO logInDTO = new LogInDTO("root@tietoevry.com", "111222cC@");

        //when
        assertThatThrownBy(() -> {
            restTemplate
                    .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                            , logInDTO, JwtDTO.class);
        })
                //then
                .isInstanceOf(ResourceAccessException.class);
    }

    @DisplayName("Sign in with invalid password")
    @Test
    void signInWithInvalidPassword() {
        //given
        LogInDTO logInDTO = new LogInDTO("root@tietoevry.com", "111222cC");

        //when
        ResponseEntity<JwtDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                        , logInDTO, JwtDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
        assertThat(response.getBody().getToken()).isNull();
    }

    @DisplayName("Sign in with unactivated account")
    @Test
    void signInUnactivatedAcc() {
        //given
        SignUpDTO signUpDTO = new SignUpDTO("testFirst", "testLast","test@tietoevry.com", "123456aB@",null);

        //when
        ResponseEntity<MessageDTO> signUpResponse = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signup"
                        , signUpDTO, MessageDTO.class);

        LogInDTO logInDTO = new LogInDTO("test@tietoevry.com", "123456aB@");

        //when
        ResponseEntity<JwtDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                        , logInDTO, JwtDTO.class);

        //then
        assertThat(response.getBody().getToken()).isEqualTo("Email is not activated");
    }

}
