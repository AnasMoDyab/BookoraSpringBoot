package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.SignUpDTO;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

//NB! Surefire pick up tests with name Test
//NB! Failsafe pick up test with name IT

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
class signUpIT {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @DisplayName("Sign up with valid information")
    @Test
    void signUpWithValidInfo() {
        //given
        SignUpDTO signUpDTO = new SignUpDTO("testFirst", "testLast","test@tietoevry.com", "123456aB@",null);

        //when
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signup"
                        , signUpDTO, MessageDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("User registered successfully!");
    }

    @DisplayName("Sign up with invalid email domain")
    @Test
    void signUpWithInvalidEmailDomain() {
        //given
        SignUpDTO signUpDTO = new SignUpDTO("testFirst", "testLast","test@abc.com", "123456aB@",null);

        //when
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signup"
                        , signUpDTO, MessageDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Error: Email domain is not valid!");
    }

    @DisplayName("Sign up with existing email domain")
    @Test
    void signUpWithExistingEmailDomain() {
        //given
        SignUpDTO signUpDTO = new SignUpDTO("testFirst", "testLast","test2@tietoevry.com", "123456aB@",null);

        //when
        restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signup"
                        , signUpDTO, MessageDTO.class);
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signup"
                        , signUpDTO, MessageDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(CONFLICT);
        assertThat(response.getBody().getMessage()).isEqualTo("Error: Email is already in use!");
    }



}