package com.jay.cloud.controller;

import com.jay.cloud.helloservice.api.dto.User;
import com.jay.cloud.helloservice.api.service.HelloService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/19.
 */
@RestController
public class RefactorHelloController implements HelloService {

    @Override
    public String hello(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @Override
    public User hello(@RequestParam("name") String name, @RequestHeader("age") Integer age) {
        return new User(name,age);
    }

    @Override
    public String hello(@RequestBody User user) {
        return "hello " + user.getName() + ", " + user.getAge();
    }
}
