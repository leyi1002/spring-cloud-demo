package com.jay.cloud.service;

import com.jay.cloud.bean.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/11/19.
 */
@FeignClient("hello-service")
public interface HelloService {

    @RequestMapping("/hello")
    String hello();

    /**
     * @RequestParam 必须制定参数名，否则报错
     * @param name
     * @return
     */
    @RequestMapping(value = "hello1",method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    /**
     * @RequestParam 和 @RequestHeader 必须指定参数名
     * @param name
     * @param age
     * @return
     */
    @RequestMapping(value = "hello2",method = RequestMethod.GET)
    User hello(@RequestParam("name") String name, @RequestHeader("age") Integer age);

    @RequestMapping(value = "hello3",method = RequestMethod.POST)
    public String hello(@RequestBody User user);

}
