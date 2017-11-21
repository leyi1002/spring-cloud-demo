package com.jay.cloud.service;

import com.jay.cloud.bean.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.util.List;
import java.util.concurrent.Future;

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
//        int i = 10/0;
        return restTemplate.getForObject("http://hello-service/users/{1}",User.class,id);
    }

    //同步执行
    @HystrixCommand(fallbackMethod = "helloFallback",threadPoolKey = "userServicePool")
    public User helloServicePool(String id){
//        int i = 10/0;
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

    /**
     * 以下两个方法是通过Command包装使用请求合并
     * @param id
     * @return
     */
    public User find(String id){
        System.out.println("调用 find");
        return restTemplate.getForObject("http://hello-service/users/{1}",User.class,id);
    }

    public List<User> findAll(List<String> id){
        System.out.println("调用 findAll");
        ParameterizedTypeReference<List<User>> responseType =
                new ParameterizedTypeReference<List<User>>(){};
        List<User> list = restTemplate.exchange("http://hello-service/findAll?ids={1}", HttpMethod.GET,
                null, responseType, StringUtils.join(id, ",")).getBody();

        //使用以下方式，将不能返回List<User>类型，而是List<LinkedHashMap>类型
        List<User> forObject = restTemplate.getForObject("http://hello-service/findAll?ids={1}",
                List.class, StringUtils.join(id, ","));
        List<User> body = restTemplate.getForEntity("http://hello-service/findAll?ids={1}",
                List.class, StringUtils.join(id, ",")).getBody();
        return list;
    }

    /**
     * 以下两个方法通过注解实现请求合并
     * @param id
     * @return
     */
    @HystrixCollapser(batchMethod = "findAllByAnnotation",collapserKey = "userCollapserCommandAnnotation",
        collapserProperties = {@HystrixProperty(name = "timerDelayInMilliseconds",value = "100")})
    public User findByAnnotation(String id){
        System.out.println("调用 findByAnnotation");
        return null;
    }

    @HystrixCommand
    public List<User> findAllByAnnotation(List<String> id){
        ParameterizedTypeReference<List<User>> responseType =
                new ParameterizedTypeReference<List<User>>(){};
        System.out.println("调用 findAllByAnnotation");
        List<User> list = restTemplate.exchange("http://hello-service/findAll?ids={1}", HttpMethod.GET,
                null, responseType, StringUtils.join(id, ",")).getBody();
        return list;
    }

}
