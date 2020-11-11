package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.services.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("ConfirmationTokenController unit test")
@ExtendWith(MockitoExtension.class)
@Tag("development")
class ConfirmationTokenControllerTest {

    @Mock
    ConfirmationTokenService confirmationTokenService;

    @InjectMocks
    ConfirmationTokenController confirmationTokenController;

    MockMvc mockMvc;

    String url;

    @BeforeEach
    void setUp() {
        url = "confirm.html";
        given(confirmationTokenService.checkToken(any())).willReturn(url);
        mockMvc = MockMvcBuilders.standaloneSetup(confirmationTokenController).build();
    }

    @DisplayName("Test confirm account with MockMvc")
    @Tag("UnitTest")
    @Test
    void confirmAccountMvcTest() throws Exception {
        mockMvc.perform(get("/confirm-account").param("token", "abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("confirm.html"));
    }

}