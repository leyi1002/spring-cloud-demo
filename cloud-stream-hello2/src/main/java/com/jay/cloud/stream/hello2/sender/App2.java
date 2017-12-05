package com.jay.cloud.stream.hello2.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/12/3.
 */
@Component
public class App2 {

    private static final Logger log = LoggerFactory.getLogger(App2.class);

    @Autowired
    private Processor processor;

    public void publishMessage(String content){
        processor.output().send(MessageBuilder.withPayload(content).build());
    }

}