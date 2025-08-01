package com.example.demo2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lina.fan
 * Created on 2025/7/31 16:06
 */
@FeignClient(value = "test-server", url = "http://localhost:8081")
public interface CustomUserDetailsFeignClient {

    @GetMapping("/userDetail")
    ResponseEntity<UserDetails> userDetail(@RequestParam String id);
}
