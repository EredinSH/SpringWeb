package com.crud.tasks.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.crud.tasks.domain.Mail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {

        //GIVEN
        Mail mail = Mail.builder()
                .mailTo("test@test.com")
                .subject("Test")
                .message("Test Message")
                .toCc("Test CC")
                .build();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc((mail.getToCc()));

        //WHEN
        simpleEmailService.send(mail);

        //THEN
        verify(javaMailSender, times(1)).send(mailMessage);
        assertThat(mail.getMailTo()).isEqualTo("test@test.com");
        assertThat(mail.getSubject()).isEqualTo("Test");
        assertThat(mail.getMessage()).isEqualTo("Test Message");
        assertThat(mail.getToCc()).isEqualTo("Test CC");
    }

}