package com.example.registrationlogin.registration;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String> {
    public boolean test(String email) {
        return true;
    }
}
