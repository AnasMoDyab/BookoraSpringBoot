package com.tietoevry.bookorabackend.integrationTest;

import com.tietoevry.bookorabackend.api.v1.model.BookingDTO;
import com.tietoevry.bookorabackend.api.v1.model.BookingIdDTO;
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
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("Development")
@Tag("IntegrationTest")
public class bookingIT {

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

    @DisplayName("Booking with valid JWT")
    @Test
    void bookingWithValidJWT() {
        //given
        BookingDTO bookingDTO = new BookingDTO(LocalDate.of(20022,11,11), 1L, 1L);
        headers.set("Authorization", "Bearer " + validJwt);

        HttpEntity<BookingDTO> request = new HttpEntity<>(bookingDTO, headers);

        //when
        ResponseEntity<BookingIdDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + BookingController.BASE_URL + "/book"
                        , request, BookingIdDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody().getMessage()).isEqualTo("Booking success");
        assertThat(response.getBody().getBookingId()).isNotNull();
    }

    @DisplayName("Booking with invalid employee ID")
    @Test
    void bookingWithInvalidEmployeeID() {
        //given
        BookingDTO bookingDTO = new BookingDTO(LocalDate.now(), 1000L, 1L);
        headers.set("Authorization", "Bearer " + validJwt);

        HttpEntity<BookingDTO> request = new HttpEntity<>(bookingDTO, headers);

        //when
        ResponseEntity<BookingIdDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + BookingController.BASE_URL + "/book"
                        , request, BookingIdDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Employee is not found");
    }

    @DisplayName("Booking with invalid zone ID")
    @Test
    void bookingWithInvalidZoneID() {
        //given
        BookingDTO bookingDTO = new BookingDTO(LocalDate.now(), 1L, 100L);
        headers.set("Authorization", "Bearer " + validJwt);

        HttpEntity<BookingDTO> request = new HttpEntity<>(bookingDTO, headers);

        //when
        ResponseEntity<BookingIdDTO> response = restTemplate
                .postForEntity("http://localhost:" + port + BookingController.BASE_URL + "/book"
                        , request, BookingIdDTO.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Zone is not found");
    }

    @DisplayName("Booking with invalid JWT")
    @Test
    void bookingWithInvalidJWT() {
        //given
        BookingDTO bookingDTO = new BookingDTO(LocalDate.now(), 1L, 1L);
        headers.set("Authorization", "Bearer " + inValidJwt);

        HttpEntity<BookingDTO> request = new HttpEntity<>(bookingDTO, headers);

        //when
        assertThatThrownBy(() -> {
            restTemplate
                    .postForEntity("http://localhost:" + port + BookingController.BASE_URL + "/book"
                            , request, BookingIdDTO.class);
        })
                //then
                .isInstanceOf(ResourceAccessException.class);
    }

}
