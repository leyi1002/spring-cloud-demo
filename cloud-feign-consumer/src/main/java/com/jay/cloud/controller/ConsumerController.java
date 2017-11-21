package com.jay.cloud.controller;

import com.jay.cloud.bean.User;
import com.jay.cloud.service.HelloService;
import com.jay.cloud.service.RefactorHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/19.
 */
@RestController
public class ConsumerController {

    @Autowired
    private HelloService helloService;
    @Autowired
    private RefactorHelloService refactorHelloService;

    @RequestMapping(value = "/feign-consumer",method = RequestMethod.GET)
    public String helloConsumer(){
        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2",method = RequestMethod.GET)
    public String helloConsumer2(){
        StringBuilder sb = new StringBuilder();
        sb.append(helloService.hello()).append("\r\n");
        sb.append(helloService.hello("DIDI")).append("\r\n");
        sb.append(helloService.hello("DIDI",30)).append("\r\n");
        sb.append(helloService.hello(new User("DIDI",30))).append("\r\n");
        return sb.toString();
    }

    @RequestMapping(value = "/feign-consumer3",method = RequestMethod.GET)
    public String helloConsumer3(){
        StringBuilder sb = new StringBuilder();
        sb.append(refactorHelloService.hello("MIMI")).append("\r\n");
        sb.append(refactorHelloService.hello("MIMI",20)).append("\r\n");
        sb.append(refactorHelloService.hello(new com.jay.cloud.helloservice.api.dto.User("MIMI",20))).append("\r\n");
        return sb.toString();
    }
}
