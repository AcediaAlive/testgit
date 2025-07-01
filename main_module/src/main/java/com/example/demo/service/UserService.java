package com.example.demo.service;

import com.example.demo.entity.UserEntity;

public interface UserService {
    boolean authUser(String id, String password);
}
