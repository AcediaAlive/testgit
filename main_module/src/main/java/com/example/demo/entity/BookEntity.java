package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("book")
public class BookEntity extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String author;
    private String publisher;
    private double price;
    private int quantity;
}
