package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConController {
    @Autowired
    private RestTemplate template;

    @RequestMapping("/begin")
    public String begin(){
        String result = template.getForObject("http://test-server/test", String.class);
        return result;
    }
}
