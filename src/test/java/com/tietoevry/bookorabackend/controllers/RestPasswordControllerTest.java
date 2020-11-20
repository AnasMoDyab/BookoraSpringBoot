package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import com.tietoevry.bookorabackend.services.RestPasswordService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("RestpasswordController unit test")
@Tag("development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class RestPasswordControllerTest {

    @Mock
    RestPasswordService restPasswordService;

    @InjectMocks
    RestPasswordController restpasswordController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restpasswordController).build();
    }

    @AfterEach
    void tearDown() {
        reset(restPasswordService);
    }

    @Test
    void responsePasswordRest() throws Exception {
        //given
        given(restPasswordService.checkCode(any())).willReturn(true);

        //when
        mockMvc.perform(get("/confirm-reset").param("codeConfirmationDto", "123456"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    void resetUserPassword() throws Exception {
        //given
        given(restPasswordService.updatePassword(any())).willReturn(new MessageDTO("test"));

        //when
        mockMvc.perform(post("/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"abc@tietoevry.com\",\"password\":\"123456xB@\"}"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("test")));

    }
}