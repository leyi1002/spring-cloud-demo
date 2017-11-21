package com.jay.cloud.service;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by Administrator on 2017/11/19.
 */
@FeignClient("hello-service")
public interface RefactorHelloService extends com.jay.cloud.helloservice.api.service.HelloService{
}
