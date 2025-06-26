package com.example.demo.controller;

import com.example.demo.entity.BookEntity;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class SthController {
    private final BookService bookService;

    @RequestMapping("/test")
    public String test(@RequestParam(required = false, defaultValue = "A") String name){
        return name+" Hello World!!!";
    }

    @PostMapping("/add")
    public BookEntity addBook(@RequestBody BookEntity book){
        bookService.addBook(book);
        return book;
    }

    @GetMapping("/findN")
    public ResponseEntity<BookEntity> findBookByName(@RequestParam String name){
        BookEntity book=bookService.findBookByName(name);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/findP")
    public ResponseEntity<List<BookEntity>> findBookByPrice(@RequestParam double price){
        List<BookEntity> lb=bookService.findBookByPrice(price);
        return ResponseEntity.ok(lb);
    }
}
