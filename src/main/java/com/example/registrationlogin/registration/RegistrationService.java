package com.example.registrationlogin.registration;

import com.example.registrationlogin.appuser.AppUser;
import com.example.registrationlogin.appuser.AppUserRole;
import com.example.registrationlogin.appuser.AppUserService;
import com.example.registrationlogin.dto.ApiResponse;
import com.example.registrationlogin.email.EmailNotificationRequest;
import com.example.registrationlogin.email.EmailSender;
import com.example.registrationlogin.email.Recipient;
import com.example.registrationlogin.registration.token.ConfirmationToken;
import com.example.registrationlogin.registration.token.ConfirmationTokenService;
import com.example.registrationlogin.utils.AppUtilities;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
   
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    
    public ApiResponse register(RegistrationRequest request) throws FileNotFoundException {
        boolean isValidEmail =
                emailValidator.test (request.getEmail ());
        
        if (!isValidEmail) {
            throw new IllegalStateException ("Invalid email");
        }
        
        String token = appUserService.signup (
                new AppUser (
                        request.getFirstName (),
                        request.getLastName (),
                        request.getEmail (),
                        request.getPassword (),
                        request.getUsername (),
                        AppUserRole.USER_ROLE
                )
        );
        EmailNotificationRequest notificationRequest = buildEmailNotificationRequest (
                request.getEmail (),
                request.getFirstName (),
                token
        );
        String response = emailSender.sendMail (notificationRequest);
        if (response == null) {
            return getRegisterFailureResponse();
        }
        return ApiResponse.builder()
                .isSuccess (true)
                .message ("Welcome Voter "+request.getFirstName ())
                .statusCode (HttpStatus.CREATED.value ())
                .build();
    }
    
    private static ApiResponse getRegisterFailureResponse() {
        return ApiResponse.builder()
                .id(-1L)
                .isSuccess(false)
                .message("Voter Registration Failed")
                .build();
    }
    
    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getConfirmationToken (token)
                .orElseThrow (() ->
                        new IllegalStateException ("token not found"));
        
        if (confirmationToken.getConfirmedAt () != null) {
            throw new IllegalStateException ("Email already confirmed");
        }
        
        LocalDateTime expiration = confirmationToken.getExpiresAt ();
        
        if (expiration.isBefore (LocalDateTime.now ())) {
            throw new IllegalStateException ("token expired");
        }
        
        confirmationTokenService.setConfirmedAt (token);
        appUserService.enableAppUser (
                confirmationToken.getAppUser ().getEmail ()
        );
        
        return "Confirmation";
    }
    
    private EmailNotificationRequest buildEmailNotificationRequest(String email, String lastname, String token) throws FileNotFoundException {
        EmailNotificationRequest request = new EmailNotificationRequest ();
        request.getTo ().add (new Recipient (lastname, email));
        String template = AppUtilities.getVerifiedMessage ();
        String content = String.format (template, lastname, AppUtilities.getUserVerificationLink (token));
        request.setHtmlContent (content);
        return request;
    }
}
    