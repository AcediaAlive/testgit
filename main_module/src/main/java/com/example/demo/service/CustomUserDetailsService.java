package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    void createUserDetails(UserEntity newUser);

    UserEntity getUserDetailsByUsername(String userName);
}
