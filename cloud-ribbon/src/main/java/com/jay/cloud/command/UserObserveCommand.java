package com.jay.cloud.command;

import com.jay.cloud.bean.User;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/8.
 */
public class UserObserveCommand extends HystrixObservableCommand<User>{

    private RestTemplate restTemplate;
    private String id;

    public UserObserveCommand(Setter setter, RestTemplate restTemplate, String id) {
        super(setter);
        this.restTemplate = restTemplate;
        this.id = id;
    }

    @Override
    protected Observable<User> construct() {
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

    @Override
    protected Observable<User> resumeWithFallback() {
        return super.resumeWithFallback();
    }
}
