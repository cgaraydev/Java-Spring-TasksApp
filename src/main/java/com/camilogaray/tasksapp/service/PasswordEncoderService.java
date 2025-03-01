package com.camilogaray.tasksapp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encryptPassword(String password) {
        return encoder.encode(password);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}