package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailsService extends UserDetailsService {
    void createUserDetails(UserEntity newUser);

    UserEntity getUserDetailsByUsername(String userName);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
