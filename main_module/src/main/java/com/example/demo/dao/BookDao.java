package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.BookEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookDao extends BaseMapper<BookEntity> {
    BookEntity selectByName(@Param("name")String name);
    List<BookEntity> getByPrice(@Param("price")double price);
    void addBook(@Param("book")BookEntity book);
}
