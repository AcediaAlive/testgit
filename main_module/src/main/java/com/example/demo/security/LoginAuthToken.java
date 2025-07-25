package com.example.demo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginAuthToken  extends AbstractAuthenticationToken {
    private String id;
    private String password;
    public LoginAuthToken(String id, String password) {
        super(null);
        this.id = id;
        this.password = password;
        setAuthenticated(false);
    }
    public LoginAuthToken(String id, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.id = id;
        this.password = null;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }
}
