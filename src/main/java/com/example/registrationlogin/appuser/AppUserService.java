package com.example.registrationlogin.appuser;

import com.example.registrationlogin.registration.token.ConfirmationTokenService;
import com.example.registrationlogin.registration.token.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND = "user with email %s not found";
    private final AppUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConfirmationTokenService tokenService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public String signup(AppUser appUser){
        boolean userAlreadyExists = repository.findByEmail(appUser.getEmail())
                .isPresent();
        if(userAlreadyExists){
            throw new IllegalStateException("User already exists");
        }

        String encodedPassword = passwordEncoder
                .encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        repository.save(appUser);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser
        );

        tokenService.saveConfirmationToken(confirmationToken);

        //TODO: SEND EMAIL
        return "Good";
    }

    public void enableAppUser(String email){
        repository.enableAppUser(email);
    }
}
