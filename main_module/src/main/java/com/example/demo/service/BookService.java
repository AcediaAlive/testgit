package com.example.demo.service;

import com.example.demo.entity.BookEntity;
import java.util.List;


public interface BookService {
    BookEntity findBookByName(String name);
    List<BookEntity> findBookByPrice(double price);
    void addBook(BookEntity book);
}
