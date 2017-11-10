package com.jay.cloud.controller;

import com.jay.cloud.bean.User;
import com.jay.cloud.command.UserCollaperCommand;
import com.jay.cloud.command.UserCommand;
import com.jay.cloud.command.UserObserveCommand;
import com.jay.cloud.service.UserService;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */
@RestController
public class UserController {


    /**************************  命令执行方式  **********************************************************/
    /**
     * 命令方式-发射多个结果
     */
    @RequestMapping(value = "/user2/{name}",method = RequestMethod.GET)
    public Observable<User> user2(@PathVariable("name") String name) throws Exception{
        HystrixObservableCommand.Setter setter = HystrixObservableCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello-command"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000));
        UserObserveCommand userObserveCommand = new UserObserveCommand(setter, restTemplate, name);
        Observable<User> observe = userObserveCommand.observe();
        return observe;
    }


    /**
     * 注解方式-同步、异步发射一个结果
     */
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user1/{name}",method = RequestMethod.GET)
    public User user1(@PathVariable("name") String name)throws Exception{
        User user = userService.helloService(name);
        return user;   //   同步执行
//        return userService.helloServiceAsync(name).get();//   异步执行
    }

    /**
     * 命令方式-同步、异步发射一个结果
     */
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/user/{name}",method = RequestMethod.GET)
    public User user(@PathVariable("name") String name)throws Exception{
        new UserCommand(restTemplate,name).queue().get();
        UserCommand.flushCache("CommandName",name);//清空缓存，下一行命令又会真实调用一次
        new UserCommand(restTemplate,name).queue().get();
        return new UserCommand(restTemplate,name).queue().get(); //异步执行
    }

    /**************************  请求合并  **********************************************************/
    /**
     * 命令方式
     * @param name
     * @return
     */
    @RequestMapping(value = "/collapser/{name}",method = RequestMethod.GET)
    public User collapser(@PathVariable("name") String name){
        User user = new UserCollaperCommand(userService, name).execute();
        User user1 = new UserCollaperCommand(userService, name).execute();
        System.out.println(user);
        return user;
    }

    /**
     * 注解方式
     * @param name
     * @return
     */
    @RequestMapping(value = "/collapserAnnotation/{name}",method = RequestMethod.GET)
    public User collapserAnnotation(@PathVariable("name") String name){
        User byAnnotation = userService.findByAnnotation(name);
        System.out.println(byAnnotation);
        return byAnnotation;
    }

    @RequestMapping(value = "/all/{name}",method = RequestMethod.GET)
    public List<User> all(@PathVariable("name") String name){
        List<User> all = userService.findAll(Arrays.asList(name));
        System.out.println(all);
        return all;
    }

}
