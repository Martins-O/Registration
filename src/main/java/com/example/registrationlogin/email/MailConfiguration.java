package com.example.registrationlogin.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Getter
@Setter
@Configuration
public class MailConfiguration {
    @Value("${mail.api.key}")
    private String mailApiKey;

    @Value("${sendinblue.mail.url}")
    private String mailUrl;


    @Bean
    public MailConfiguration mailConfig(){
        return new MailConfiguration (mailApiKey, mailUrl);
    }

//    private String apikey;
//    private String mailUrl;
}
