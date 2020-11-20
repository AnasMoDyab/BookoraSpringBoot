package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.JwtDTO;
import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.api.v1.model.ZoneSettingDTO;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import com.tietoevry.bookorabackend.controllers.ZoneController;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
public class ZoneSettingIT {

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    String validAdminJwt;
    String validUserJwt;
    String inValidJwt;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        //get valid admin jwt
        LogInDTO logInDTO = new LogInDTO("root@tietoevry.com", "123456aB@");
        ResponseEntity<JwtDTO> jwtResponse = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                        , logInDTO, JwtDTO.class);
        validAdminJwt = jwtResponse.getBody().getToken();

        //get valid user jwt
        LogInDTO logInUserDTO = new LogInDTO("employee3@tietoevry.com", "123456aB@");
        ResponseEntity<JwtDTO> jwtUserResponse = restTemplate
                .postForEntity("http://localhost:" + port + EmployeeController.BASE_URL + "/signin"
                        , logInUserDTO, JwtDTO.class);
        validUserJwt = jwtUserResponse.getBody().getToken();

        // set Invalid JWT
        inValidJwt = "test";

        // set header to JSON
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @DisplayName("Edit a zone by admin")
    @Test
    void editAZoneByAdmin() {
        //given
        ZoneSettingDTO zoneSettingDTO = new ZoneSettingDTO(1, 1L, 100, true);
        headers.set("Authorization", "Bearer " + validAdminJwt);
        HttpEntity<ZoneSettingDTO> request = new HttpEntity<>(zoneSettingDTO, headers);

        //when
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + ZoneController.BASE_URL + "/ZoneSettings"
                        , request, MessageDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getMessage()).isEqualTo("Modified successfully");
    }

    @DisplayName("Edit a zone by user")
    @Test
    void editAZoneByUser() {
        //given
        ZoneSettingDTO zoneSettingDTO = new ZoneSettingDTO(1, 2L, 100, true);
        headers.set("Authorization", "Bearer " + validUserJwt);
        HttpEntity<ZoneSettingDTO> request = new HttpEntity<>(zoneSettingDTO, headers);

        //when
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + ZoneController.BASE_URL + "/ZoneSettings"
                        , request, MessageDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
    }

    @DisplayName("Edit a non-existing zone by admin")
    @Test
    void editANonExistingZoneByUser() {
        //given
        ZoneSettingDTO zoneSettingDTO = new ZoneSettingDTO(1, 100L, 100, true);
        headers.set("Authorization", "Bearer " + validAdminJwt);
        HttpEntity<ZoneSettingDTO> request = new HttpEntity<>(zoneSettingDTO, headers);

        //when
        ResponseEntity<MessageDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + ZoneController.BASE_URL + "/ZoneSettings"
                        , request, MessageDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

}
