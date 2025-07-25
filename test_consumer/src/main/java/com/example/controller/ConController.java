package com.example.controller;

import com.example.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConController {
    @Autowired
    private RestTemplate template;
    @Autowired
    ConsumerService consumerService;

    @RequestMapping("/begin")
    public String begin(){
        String result = template.getForObject("http://test-server/home/test", String.class);
        return result;
    }
    @RequestMapping("/begin2")
    public String begin2(@RequestParam(required = false, defaultValue = "A") String name){
        return consumerService.test2(name);
    }

    @RequestMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password){
        return consumerService.auth(id, password);
    }
}
