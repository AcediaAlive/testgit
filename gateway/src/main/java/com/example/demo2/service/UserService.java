package com.example.demo2.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "test-server",contextId = "userClient")
public interface UserService {
    @GetMapping("/auth/user")
    boolean authUser(@RequestParam String id, @RequestParam String password);
}
