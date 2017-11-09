package com.jay.cloud.service;

import com.jay.cloud.bean.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/11/8.
 */
@Service
public class UserService {
    @Autowired
    private RestTemplate restTemplate;

    //同步执行
    @HystrixCommand(fallbackMethod = "helloFallback")
    public User helloService(String id){
        return restTemplate.getForObject("http://hello-service/users/{1}",User.class,id);
    }

    //异步执行
    @HystrixCommand(fallbackMethod = "helloFallback")
    public Future<User> helloServiceAsync(String id){
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://HELLO-SERVICE/users/{1}",User.class,id);
            }
        };
    }

    /**
     * EAGER:表示observe()模式，LAZY:表示toObservable()模式
     * @param id
     * @return
     */
//    @HystrixCommand(fallbackMethod = "helloFallback",observableExecutionMode = ObservableExecutionMode.EAGER)
    @HystrixCommand(fallbackMethod = "helloFallback",observableExecutionMode = ObservableExecutionMode.LAZY)
    public Observable<User> helloServiceObservable(String id){
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> observable) {
                try {
                    if(!observable.isUnsubscribed()){
                        User user = restTemplate.getForObject("http://hello-service/users/{1}", User.class, id);
                        observable.onNext(user);
                        observable.onCompleted();
                    }
                } catch (RestClientException e) {
                    observable.onError(e);
                }
            }
        });
    }

    public User helloFallback(String name,Throwable e){
        String message = e.getMessage();
        System.out.println("ERROR: "+message);
        User user = new User();
        user.setName("error name"+name);
        return user;
    }

}
