package com.example.demo.security;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginAuthProvider implements AuthenticationProvider {
    private final UserService userService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("LoginAuthProvider");
        LoginAuthToken loginAuthToken = (LoginAuthToken) authentication;
        Object principal = authentication.getPrincipal();
        String id = "";
        if (principal instanceof String) {
            id = (String) principal;
        }
        String password = (String) authentication.getCredentials();
        if(userService.authUser(id,password)){
            LoginAuthToken authenticationResult = new LoginAuthToken(id, password);
            authenticationResult.setDetails(loginAuthToken.getDetails());
            return authenticationResult;
        }else{
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthToken.class.isAssignableFrom(authentication);
    }
}
