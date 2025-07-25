package com.example.demo2.service.impl;

import com.example.demo2.dao.UserDao;
import com.example.demo2.entity.UserEntity;
import com.example.demo2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean authUser(String id, String password) {
//        用数据库
        String password2=userDao.selectById(id).getPassword();
        return password.equals(password2);

//        用redis
//        String password2=(String)redisTemplate.opsForValue().get(id);
//        if(password2!=null){
//            return password.equals(password2);
//        }
//        return false;
    }

    @Override
    public void addUser(UserEntity user) {
        userDao.insert(user);
    }
}
