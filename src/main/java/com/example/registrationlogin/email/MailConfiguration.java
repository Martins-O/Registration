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
public class MailConfiguration {
    private String apikey;
    private String mailUrl;
}
