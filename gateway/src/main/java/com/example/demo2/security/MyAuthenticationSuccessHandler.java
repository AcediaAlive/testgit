package com.example.demo2.security;

import com.example.demo2.service.CustomUserDetailsService;
import com.example.demo2.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final RedisTemplate<String, Object> redisTemplate;
    private final CustomUserDetailsService customUserDetailsService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("onAuthenticationSuccess");
        String userName = (String) authentication.getPrincipal();
        UserDetails userDetailsInfoDb = customUserDetailsService.loadUserByUsername(userName);
        if(userDetailsInfoDb==null){
            System.out.println(userName);
        }
        final String accessToken = JwtUtil.generateJwt(userName);
        redisTemplate.opsForValue().set(userName,accessToken,20, TimeUnit.MINUTES);

        // 返回token给前端     前端拿到token 每次请求都要在head里携带token
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("token", accessToken);
    }
}
