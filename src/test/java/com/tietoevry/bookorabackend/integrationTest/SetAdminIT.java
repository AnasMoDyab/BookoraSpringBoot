package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.EmailDTO;
import com.tietoevry.bookorabackend.api.v1.model.JwtDTO;
import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
public class SetAdminIT {

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

    @LocalServerPort
    private int port;

    String validJwt;
    String inValidJwt;

    @BeforeEach
    void setUp() {
        //get valid jwt
        LogInDTO logInDTO = new LogInDTO("root@tietoevry.com", "123456aB@");
        ResponseEntity<JwtDTO> jwtResponse = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                        , logInDTO, JwtDTO.class);
        validJwt = jwtResponse.getBody().getToken();

        // set Invalid JWT
        inValidJwt = "test";

        // set header to JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @DisplayName("Set a user to admin with valid JWT")
    @Test
    void setAUserToAdminWithValidJWT(){
        EmailDTO emailDTO = new EmailDTO("employee1@tietoevry.com", new HashSet<String>(Arrays.asList("user", "admin")));
        headers.set("Authorization", "Bearer " + validJwt);

        HttpEntity<EmailDTO> request = new HttpEntity<>(emailDTO, headers);

        //when
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/updateEmployee"
                        , request, MessageDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Updated success");
    }

    @DisplayName("Set a non-existing user to admin with valid JWT")
    @Test
    void setANonExistingUserToAdminWithValidJWT(){
        EmailDTO emailDTO = new EmailDTO("abc@tietoevry.com", new HashSet<String>(Arrays.asList("user", "admin")));
        headers.set("Authorization", "Bearer " + validJwt);

        HttpEntity<EmailDTO> request = new HttpEntity<>(emailDTO, headers);

        //when
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/updateEmployee"
                        , request, MessageDTO.class);

        //then
        System.out.println(response);
    }

}
