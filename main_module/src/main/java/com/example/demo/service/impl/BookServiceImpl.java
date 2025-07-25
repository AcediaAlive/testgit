package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.dao.BookDao;
import com.example.demo.entity.BookEntity;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    @Override
    public BookEntity findBookByName(String name) {
        return bookDao.selectByName(name);
    }

    @Override
    public List<BookEntity> findBookByPrice(double price) {
        return bookDao.getByPrice(price);
    }

    @Override
    public void addBook(BookEntity book) {
        bookDao.insert(book);
    }
}
