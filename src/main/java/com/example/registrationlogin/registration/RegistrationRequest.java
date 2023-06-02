package com.example.registrationlogin.registration;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
