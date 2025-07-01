package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Override
    public boolean authUser(String id, String password) {
        String password2=userDao.selectById(id).getPassword();
        return password.equals(password2);
    }
}
