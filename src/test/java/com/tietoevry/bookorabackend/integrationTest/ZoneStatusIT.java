package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.FloorDateDTO;
import com.tietoevry.bookorabackend.api.v1.model.JwtDTO;
import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.api.v1.model.StatusOfAZoneOnADayDTO;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import com.tietoevry.bookorabackend.controllers.ZoneController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
public class ZoneStatusIT {

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    String validJwt;
    String inValidJwt;
    @LocalServerPort
    private int port;

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

    @DisplayName("Check status of all zone in a floor with valid JWT")
    @Test
    void checkStatusOfAllZoneInAFloor() {
        //given
        FloorDateDTO floorDateDTO = new FloorDateDTO(1, LocalDate.now());
        headers.set("Authorization", "Bearer " + validJwt);
        HttpEntity<FloorDateDTO> request = new HttpEntity<>(floorDateDTO, headers);

        //when
        ResponseEntity<List<StatusOfAZoneOnADayDTO>> response = restTemplate
                .exchange("http://localhost:" + port + ZoneController.BASE_URL + "/checkStatusOfAllZoneInAFloor"
                        , HttpMethod.POST, request, new ParameterizedTypeReference<List<StatusOfAZoneOnADayDTO>>() {
                        });

        //then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().get(0).getCapacity()).isNotNull();
        assertThat(response.getBody().get(0).getTotalReservation()).isNotNull();
        assertThat(response.getBody().get(0).getBookedPercentage()).isNotNull();
        assertThat(response.getBody().size()).isNotNull();
    }

    @DisplayName("Check status of all zone in a floor with invalid JWT")
    @Test
    void checkStatusOfAllZoneInAFloorWithInvalidJWT() {
        //given
        FloorDateDTO floorDateDTO = new FloorDateDTO(1, LocalDate.now());
        headers.set("Authorization", "Bearer " + inValidJwt);
        HttpEntity<FloorDateDTO> request = new HttpEntity<>(floorDateDTO, headers);

        //when
        assertThatThrownBy(() -> {
            restTemplate
                    .exchange("http://localhost:" + port + ZoneController.BASE_URL + "/checkStatusOfAllZoneInAFloor"
                            , HttpMethod.POST, request, new ParameterizedTypeReference<List<StatusOfAZoneOnADayDTO>>() {
                            });
        })
                //then
                .isInstanceOf(ResourceAccessException.class);
    }
}
