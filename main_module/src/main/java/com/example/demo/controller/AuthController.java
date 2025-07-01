package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    @RequestMapping("/login")
    public String auth(@RequestParam(required = false, defaultValue = "A") String name){
        return name+" Hello World!!!";
    }
}
