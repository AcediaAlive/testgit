package com.example.demo2.service.impl;

import com.example.demo2.feign.CustomUserDetailsFeignClient;
import com.example.demo2.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lina.fan
 * Created on 2025/7/30 18:07
 */
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Resource
    private CustomUserDetailsFeignClient customUserDetailsFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customUserDetailsFeignClient.userDetail(username).getBody();
    }
}
