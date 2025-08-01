package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.selectById(username);
    }
    @Override
    public void createUserDetails(UserEntity newUser) {
        userDao.insert(newUser);
    }

    @Override
    public UserEntity getUserDetailsByUsername(String id) {
        return userDao.selectById(id);
    }
}
