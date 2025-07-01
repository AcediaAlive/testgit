package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("user")
public class UserEntity extends BaseEntity {
    private String id;
    private String password;
}
