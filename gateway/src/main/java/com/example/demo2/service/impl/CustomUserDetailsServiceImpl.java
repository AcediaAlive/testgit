package com.example.demo2.service.impl;

import com.example.demo2.dao.UserDao;
import com.example.demo2.entity.UserEntity;
import com.example.demo2.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userDao.selectById(id);
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
