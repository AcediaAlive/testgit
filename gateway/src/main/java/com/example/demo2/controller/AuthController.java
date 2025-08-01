package com.example.demo2.controller;

import com.example.demo2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/get-token")
    public String getToken(@RequestParam String id) {
        System.out.println("getToken");
        return JwtUtil.generateJwt(id);
    }
}
