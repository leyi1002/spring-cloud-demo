package com.jay.cloud.stream.hello.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * Created by Administrator on 2017/12/3.
 */
@EnableBinding(value = Processor.class)
public class App1Listener {

    private static final Logger log = LoggerFactory.getLogger(App1Listener.class);

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public String receiveFromInput(Object payload) throws InterruptedException {
        log.info("Received : {}",payload);
        Thread.sleep(10000);
        return "From App1 Return-"+payload;
    }


}
