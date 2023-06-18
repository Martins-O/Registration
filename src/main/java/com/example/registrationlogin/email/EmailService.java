package com.example.registrationlogin.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class EmailService implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final MailConfiguration mailConfig;
    @Override
    @Async
    public String sendMail(EmailNotificationRequest emailNotificationRequest) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", mailConfig.getApikey ());
        HttpEntity<EmailNotificationRequest> requestHttpEntity = new HttpEntity<> (emailNotificationRequest, headers);

        ResponseEntity<String> response =
                template.postForEntity(mailConfig.getMailUrl(), requestHttpEntity, String.class);
        return response.getBody();
    }
}
