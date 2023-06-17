package com.example.registrationlogin.email;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailNotificationRequest {
    private final Sender sender = new Sender("Registration", "noreply@Registration.co.uk");
    private List<Recipient> to = new ArrayList<>();
    private final String subject = "Welcome to VotingApp";
    private String htmlContent;
}
