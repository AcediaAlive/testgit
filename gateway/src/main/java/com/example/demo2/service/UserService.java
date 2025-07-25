package com.example.demo2.service;

import com.example.demo2.entity.UserEntity;

public interface UserService {
    boolean authUser(String id, String password);
    void addUser(UserEntity user);
}
