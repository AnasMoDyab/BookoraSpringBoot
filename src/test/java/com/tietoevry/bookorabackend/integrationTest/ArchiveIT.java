package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.AdminBookingForAllDTO;
import com.tietoevry.bookorabackend.api.v1.model.BookingListDTOAdmin;
import com.tietoevry.bookorabackend.api.v1.model.JwtDTO;
import com.tietoevry.bookorabackend.api.v1.model.LogInDTO;
import com.tietoevry.bookorabackend.controllers.BookingController;
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

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
public class ArchiveIT {

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

    @DisplayName("Get all bookings in a period by Admin")
    @Test
    void getAllBookingsInAPeriodByAdmin(){
        //given
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(7);
        AdminBookingForAllDTO adminBookingForAllDTO = new AdminBookingForAllDTO(from, to);
        headers.set("Authorization", "Bearer " + validAdminJwt);

        HttpEntity<AdminBookingForAllDTO> request = new HttpEntity<>(adminBookingForAllDTO, headers);

        //when
        ResponseEntity<BookingListDTOAdmin> response = restTemplate
                .postForEntity("http://localhost:" + port + BookingController.BASE_URL + "/getAllBookingOfEmployeesInAPeriodAdmin"
                        , request, BookingListDTOAdmin.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getBookingofEmployeeDTOList()).isNotNull();
    }

    @DisplayName("Get all bookings in a period by User")
    @Test
    void getAllBookingsInAPeriodByUser(){
        //given
        LocalDate from = LocalDate.now();
        LocalDate to = LocalDate.now().plusDays(7);
        AdminBookingForAllDTO adminBookingForAllDTO = new AdminBookingForAllDTO(from, to);
        headers.set("Authorization", "Bearer " + validUserJwt);

        HttpEntity<AdminBookingForAllDTO> request = new HttpEntity<>(adminBookingForAllDTO, headers);

        //when
        ResponseEntity<BookingListDTOAdmin> response = restTemplate
                .postForEntity("http://localhost:" + port + BookingController.BASE_URL + "/getAllBookingOfEmployeesInAPeriodAdmin"
                        , request, BookingListDTOAdmin.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
    }

}
