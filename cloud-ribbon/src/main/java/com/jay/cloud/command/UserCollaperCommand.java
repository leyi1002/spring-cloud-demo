package com.jay.cloud.command;

import com.jay.cloud.bean.User;
import com.jay.cloud.service.UserService;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/11/9.
 */
public class UserCollaperCommand extends HystrixCollapser<List<User>,User,String> {

    private UserService userService;
    private String userId;

    public UserCollaperCommand(UserService userService, String userId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapserCommand"))
        .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    public String getRequestArgument() {
        return userId;
    }

    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, String>> collapsedRequests) {
        List<String> userIds = new ArrayList<>(collapsedRequests.size());
        userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument)
            .collect(Collectors.toList()));
        UserBatchCommand userBatchCommand = new UserBatchCommand(userService, userIds);
        return userBatchCommand;
    }

    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, String>> collapsedRequests) {
        int count = 0;
        for(CollapsedRequest<User,String> collapsedRequest:collapsedRequests){
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }



    }
}
