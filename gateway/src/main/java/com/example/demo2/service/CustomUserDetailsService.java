package com.example.demo2.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "test-server",contextId = "CustomUserClient")
public interface CustomUserDetailsService extends UserDetailsService {
    @GetMapping("/home/createUD")
    void createUserByDetails(@RequestBody UserDetails newUser);
    @GetMapping("/home/loadUD")
    UserDetails getUserDetailsByUsername(@RequestParam String userName);
}
