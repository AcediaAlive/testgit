package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    @GetMapping("/get-token")
    public String getToken(@RequestParam String id, @RequestParam String password) {
        if(userService.authUser(id, password)){
            return JwtUtil.generateJwt(id);
        }else{
            return "Invalid username or password";
        }
    }
}
