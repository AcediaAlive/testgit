package com.example.demo.controller;

import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/get-token")
    public String getToken(@RequestParam String id, @RequestParam String password) {
        System.out.println("getToken");
        if(redisTemplate.hasKey(id)){
            return (String)redisTemplate.opsForValue().get(id);
        }else{
            if(userService.authUser(id, password)){
                String token= JwtUtil.generateJwt(id);
                redisTemplate.opsForValue().set(id,token);
                redisTemplate.expire(id,20, TimeUnit.MINUTES);
                return token;
            }else{
                return "Invalid username or password";
            }
        }
    }
}
