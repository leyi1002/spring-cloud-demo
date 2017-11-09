package com.jay.cloud.command;

import com.jay.cloud.bean.User;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2017/11/7.
 */
public class UserCommand extends HystrixCommand<User> {

    private RestTemplate restTemplate;
    private String id;

    public UserCommand(Setter setter, RestTemplate restTemplate, String id) {
        super(setter);
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
