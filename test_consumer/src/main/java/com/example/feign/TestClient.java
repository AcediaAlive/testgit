package com.example.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "test-server")
public interface TestClient {
    @GetMapping("/home/test")
    String test2(@RequestParam String name);
    @GetMapping("/auth/get-token")
    String auth(@RequestParam String id, @RequestParam String password);
}
