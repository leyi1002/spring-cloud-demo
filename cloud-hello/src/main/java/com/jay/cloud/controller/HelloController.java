package com.jay.cloud.controller;

import com.jay.cloud.bean.User;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * Created by Administrator on 2017/9/17.
 */
@RestController
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Registration registration;
    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(Integer s) throws InterruptedException {
        // 测试api网关的HystrixCommand 执行超时时间
        if(s != null){
            if(s == 1){
                Thread.sleep(1000);
            }else if(s == 1.5){
                Thread.sleep(1500);
            }else if(s == 2){
                Thread.sleep(2001);
            }
        }
        ServiceInstance instance = serviceInstance();
        logger.info("/hello,host:"+instance.getHost()+",service_id"+instance.getServiceId());
        return "hello world";
    }

    @RequestMapping(value = "/api/hello",method = RequestMethod.GET)
    public String helloapi(){
        ServiceInstance instance = serviceInstance();
        logger.info("/hello,host:"+instance.getHost()+",service_id"+instance.getServiceId());
        return "hello world";
    }

    @RequestMapping(value = "/users/{id}",method = RequestMethod.GET)
    public User users(@PathVariable("id") String name){
        User user = new User();
        user.setName(name);
        return user;
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET)
    public List<User> users1(String ids){
        List<User> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(ids)){
            String[] names = ids.split(",");
            User user = null;
            for(int i=0;i <names.length;i++){
                user = new User();
                user.setName(names[i]);
                list.add(user);
            }
        }
        User user = new User();user.setName("a");
        User user1 = new User();user1.setName("b");

        list.add(user);
        list.add(user1);
        return list;
    }

    @RequestMapping(value = "/hello1",method = RequestMethod.GET)
    public String hello(@RequestParam String name){
        return "hello" + name;
    }

    @RequestMapping(value = "/hello2",method = RequestMethod.GET)
    public User hello(@RequestParam String name,@RequestHeader Integer age){
        return new User(name,age);
    }

    @RequestMapping(value = "/hello3",method = RequestMethod.POST)
    public String hello(@RequestBody User user){
        return "hello" + user.getName() + ", " + user.getAge();
    }

    /**检查登录重定向是否经过网关**/
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String host = request.getHeader("Host");
        response.addCookie(new Cookie("login","success"));
        response.sendRedirect("/api-a/loginSuccess?accessToken=1");
        String location = response.getHeader("Location");
        logger.info("login------Location: {},Host: {}",location,host);
    }

    @RequestMapping(value = "/loginSuccess",method = RequestMethod.GET)
    public String loginSuccess(HttpServletRequest request){
        String host = request.getHeader("Host");
        Cookie[] cookies = request.getCookies();
        for(Cookie c: cookies){
            if("login".equals(c.getName())){
                logger.info("cookie bind success");
            }
        }
        logger.info("loginSuccess------ Host: {}",host);
        return "loginSuccess";
    }

    private ServiceInstance serviceInstance() {
        String serviceId = registration.getServiceId();
        List<ServiceInstance> instances = client.getInstances(serviceId);
        if(instances != null && instances.size() > 0){
            return instances.get(0);
        }
        return  null;
    }


}
