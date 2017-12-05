package com.jay.cloud.stream.hello2.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * Created by Administrator on 2017/12/3.
 */
@EnableBinding(Processor.class)
public class App2Listener {

    private static final Logger log = LoggerFactory.getLogger(App2Listener.class);

    @StreamListener(Processor.INPUT)
    public void receiveFromOuput(Object payload){
        log.info("{}",payload);
    }
}