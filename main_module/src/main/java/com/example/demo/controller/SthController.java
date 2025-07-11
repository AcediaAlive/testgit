package com.example.demo.controller;

import com.example.demo.entity.BookEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/home")
@RequiredArgsConstructor
public class SthController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/test")
    public String test(@RequestParam(required = false, defaultValue = "A") String name){
        return name+" Hello World!!!";
    }

    @PostMapping("/addU")
    public UserEntity addUser(@RequestBody UserEntity user){
        userService.addUser(user);
//        redisTemplate.opsForValue().set(user.getUsername(),user.getPassword());
        return user;
    }

    @PostMapping("/addB")
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
