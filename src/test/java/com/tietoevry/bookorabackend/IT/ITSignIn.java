package com.tietoevry.bookorabackend.IT;

import com.tietoevry.bookorabackend.api.v1.model.*;
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
import static org.springframework.http.HttpStatus.CREATED;

//NB! Surefire pick up tests with name Test
//NB! Failsafe pick up test with name IT

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
class ITSignIn {

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
        System.out.println(response);
        //assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("Error: Email domain is not valid!");
    }


    @Test
    void signIn() {
        LogInDTO logInDTO = new LogInDTO("root@tietoevry.com", "123456aB@");

        ResponseEntity<JwtDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                        , logInDTO, JwtDTO.class);

        System.out.println(response);

    }

    @Test
    void test() {


        ResponseEntity<EmployeeListDTO> response = restTemplate
                .getForEntity("http://localhost:" + port + EmployeeController.BASE_URL
                        , EmployeeListDTO.class);

        System.out.println(response);

    }

}