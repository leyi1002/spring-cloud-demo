package com.jay.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    private ServiceInstance serviceInstance() {
        String serviceId = registration.getServiceId();
        List<ServiceInstance> instances = client.getInstances(serviceId);
        if(instances != null && instances.size() > 0){
            return instances.get(0);
        }
        return  null;
    }


}
