package com.example.registrationlogin.email;

import java.util.concurrent.CompletableFuture;

public interface EmailSender {
    CompletableFuture<String> sendMail(EmailNotificationRequest emailNotificationRequest);
}
