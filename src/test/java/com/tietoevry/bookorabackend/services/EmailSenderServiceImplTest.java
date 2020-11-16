package com.tietoevry.bookorabackend.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@DisplayName("EmailSenderServiceImp unit test")
@Tag("Development")
@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
class EmailSenderServiceImplTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailSenderServiceImpl emailSenderService;

    @Test
    void sendEmail() {
        //when
        emailSenderService.sendEmail(new SimpleMailMessage());

        //then
        then(javaMailSender).should(times(1)).send(any(SimpleMailMessage.class));
    }
}