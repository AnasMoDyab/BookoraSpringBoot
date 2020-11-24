package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.*;
import com.tietoevry.bookorabackend.controllers.EmployeeController;
import com.tietoevry.bookorabackend.controllers.ZoneController;
import org.junit.jupiter.api.*;
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
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
public class StatisticsIT {

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

    @DisplayName("Check status of all zones in a floor by admin")
    @Test
    void checkStatusOfAllZonesInAFloorByAdmin() {
        FloorDateDTO floorDateDTO = new FloorDateDTO(1, LocalDate.now());
        headers.set("Authorization", "Bearer " + validAdminJwt);
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

    @DisplayName("Check status of all zones in a floor by user")
    @Test
    void checkStatusOfAllZonesInAFloorByUser() {
        FloorDateDTO floorDateDTO = new FloorDateDTO(1, LocalDate.now());
        headers.set("Authorization", "Bearer " + validUserJwt);
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

    @DisplayName("Check status of all zones in a floor with invalid JWT")
    @Test
    void checkStatusOfAllZonesInAFloorWithInvalidJWT() {
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

    @Disabled
    @DisplayName("Check status of all zones in a floor in a period by admin")
    @Test
    void checkStatusOfAllZonesInAFloorInAPeriodByAdmin() {
        PeriodDTO periodDTO = new PeriodDTO(LocalDate.now(), LocalDate.now().plusDays(7));
        headers.set("Authorization", "Bearer " + validAdminJwt);
        HttpEntity<PeriodDTO> request = new HttpEntity<>(periodDTO, headers);

        //when
        ResponseEntity<TotalBookingInBuildingDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + ZoneController.BASE_URL + "/CheckStatusOfBuildingOnPeriod"
                        , request, TotalBookingInBuildingDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getTotalForAllFloor()).isNotNull();
    }

    @Disabled
    @DisplayName("Check status of all zones in a floor in a period by user")
    @Test
    void checkStatusOfAllZonesInAFloorInAPeriodByUser() {
        PeriodDTO periodDTO = new PeriodDTO(LocalDate.now(), LocalDate.now().plusDays(7));
        headers.set("Authorization", "Bearer " + validUserJwt);
        HttpEntity<PeriodDTO> request = new HttpEntity<>(periodDTO, headers);

        //when
        ResponseEntity<TotalBookingInBuildingDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + ZoneController.BASE_URL + "/CheckStatusOfBuildingOnPeriod"
                        , request, TotalBookingInBuildingDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
    }

    @Disabled
    @DisplayName("Check status of all zones in a floor in a period with invalid JWT")
    @Test
    void checkStatusOfAllZonesInAFloorInAPeriodWithInvalidJWT() {
        PeriodDTO periodDTO = new PeriodDTO(LocalDate.now(), LocalDate.now().plusDays(7));
        headers.set("Authorization", "Bearer " + inValidJwt);
        HttpEntity<PeriodDTO> request = new HttpEntity<>(periodDTO, headers);

        //when
        assertThatThrownBy(() -> {
            restTemplate
                    .postForEntity("http://localhost:" + port + ZoneController.BASE_URL + "/CheckStatusOfBuildingOnPeriod"
                            , request, TotalBookingInBuildingDTO.class);
        })
                //then
                .isInstanceOf(ResourceAccessException.class);
    }
}
