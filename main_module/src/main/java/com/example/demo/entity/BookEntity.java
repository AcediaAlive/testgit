package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("book")
public class BookEntity {
    private String id;
    private String name;
    private String author;
    private String publisher;
    private double price;
    private int quantity;
}
