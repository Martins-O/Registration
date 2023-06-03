package com.example.registrationlogin.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig {

    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("api/v1/registration/**", "/home")
                .permitAll()
              .anyRequest().authenticated()
              .and()
              .formLogin()
              .loginPage("/login")
              .permitAll()
              .and()
              .logout()
              .permitAll();
    }
}
