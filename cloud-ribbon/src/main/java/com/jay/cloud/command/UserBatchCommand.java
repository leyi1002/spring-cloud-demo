package com.jay.cloud.command;

import com.jay.cloud.bean.User;
import com.jay.cloud.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {

    private UserService userService;
    private List<String> ids;

    public UserBatchCommand(UserService userService, List<String> ids) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
        this.userService = userService;
        this.ids = ids;
    }

    @Override
    protected List<User> run() throws Exception {
        List<User> all = userService.findAll(ids);
        return all;
    }
}
