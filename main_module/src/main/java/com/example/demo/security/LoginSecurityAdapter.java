package com.example.demo.security;

import com.example.demo.interceptor.LoginAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class LoginSecurityAdapter extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private LoginAuthProvider loginAuthProvider;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("LoginSecurityAdapter.configure");
        LoginAuthFilter LoginAuthFilter = new LoginAuthFilter();
        LoginAuthFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        LoginAuthFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        http.authenticationProvider(loginAuthProvider)
                .addFilterAfter(LoginAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        http.exceptionHandling()
//                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
