package com.jay.cloud.service;

import com.jay.cloud.bean.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/13.
 */
@Service
public class ThreadPoolService {
    @Autowired
    private RestTemplate restTemplate;

    //同步执行
    @HystrixCommand(fallbackMethod = "helloFallback")
    public User sayName(String id){
//        int i = 10/0;
        return restTemplate.getForObject("http://hello-service/users/{1}",User.class,id);
    }

    public User helloFallback(String name,Throwable e){
        String message = e.getMessage();
        System.out.println("ERROR: "+message);
        User user = new User();
        user.setName("error name"+name);
        return user;
    }
}
