package com.tietoevry.bookorabackend.controllers;

import com.tietoevry.bookorabackend.api.v1.model.MessageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ControllerExceptionHandler unit test")
@Tag("development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class ControllerExceptionHandlerTest {

    @InjectMocks
    ControllerExceptionHandler controllerExceptionHandler;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controllerExceptionHandler)
                .setHandlerExceptionResolvers(new ExceptionHandlerExceptionResolver())
                .build();
    }

    @Test
    void handleRuntime() {
        //when
        MessageDTO messageDTO = controllerExceptionHandler.handleRuntime(new RuntimeException("test"));

        //then
        assertThat(messageDTO.getMessage()).isEqualTo("test");
    }
}