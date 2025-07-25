package com.example.demo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
@RequestMapping("/home")
@RequiredArgsConstructor
public class SthController {
    private final BookService bookService;
    private final UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/test")
    public String test(@RequestParam(required = false, defaultValue = "A") String name){
//        return name+" Hello World!!!";
        Entry entry = null;
        try {
            // 尝试进入资源保护，如果流量超过限制，会抛出BlockException
            SphU.entry("test");
            // 如果没有抛出异常，则返回成功消息
            return name+" Hello World!!!";
        } catch (BlockException e) {
            // 如果流量超过限制，捕获BlockException并打印堆栈信息，返回繁忙消息
            e.printStackTrace();
            return "busy......!!!";
        } finally {
            // 确保在最终块中退出Entry，释放资源
            if (entry != null) {
                entry.exit();
                // 这个return语句会覆盖上面的return，因此不会被执行到
                return "this is final";
            }
        }
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
