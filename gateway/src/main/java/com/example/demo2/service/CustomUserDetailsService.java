package com.example.demo2.service;

import com.example.demo2.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    void createUserDetails(UserEntity newUser);

    UserEntity getUserDetailsByUsername(String userName);
}
