package com.tietoevry.bookorabackend.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//NB! Surefire pick up tests with name Test
//NB! Failsafe pick up test with name IT

@SpringBootTest
@Tag("Development")
@Tag("IntegrationTest")
class ITConfirmationTokenService {

    @BeforeEach
    void setUp() {
    }

    @Test
    void checkToken() {
    }

}