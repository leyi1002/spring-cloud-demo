package com.jay.cloud.command;

import com.jay.cloud.bean.User;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/7.
 */
public class UserCommand1 extends HystrixCommand<User> {

    private RestTemplate restTemplate;
    private String id;

    public UserCommand1(RestTemplate restTemplate, String id) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("hello-command"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("UserCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(1000)));
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected User run() {
        System.out.println("CACHE ");
        return restTemplate.getForObject("http://hello-service/users/{1}",User.class,id);
    }

    @Override
    protected User getFallback() {
        User user = new User();
        user.setName("error name");
        return user;
    }

    @Override
    protected String getCacheKey() {
        return id;
    }

    /**
     * 清空缓存
     * @param commandKey
     * @param id
     */
    public static void flushCache(String commandKey,String id){
        //清空缓存
        HystrixRequestCache.getInstance(HystrixCommandKey.Factory.asKey(commandKey),
                HystrixConcurrencyStrategyDefault.getInstance()).clear(id);
    }
}
