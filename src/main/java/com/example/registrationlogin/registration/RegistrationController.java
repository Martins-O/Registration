package com.example.registrationlogin.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    
    
    private final RegistrationService registrationService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) throws FileNotFoundException {
        return ResponseEntity.ok (registrationService.register(request));
    }
    
    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }
}

