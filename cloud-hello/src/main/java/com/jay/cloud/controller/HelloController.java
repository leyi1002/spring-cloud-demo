package com.jay.cloud.controller;

import com.jay.cloud.bean.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
    public String hello(){
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

    private ServiceInstance serviceInstance() {
        String serviceId = registration.getServiceId();
        List<ServiceInstance> instances = client.getInstances(serviceId);
        if(instances != null && instances.size() > 0){
            return instances.get(0);
        }
        return  null;
    }


}
