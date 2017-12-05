package com.jay.cloud.stream.hello.receiver;

import com.jay.cloud.stream.hello.bean.User;
import com.jay.cloud.stream.hello.sender.SinkSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by Administrator on 2017/11/30.
 */
@EnableBinding(value = SinkSender.class)
public class SinkReceiver {

    private static final Logger log = LoggerFactory.getLogger(SinkReceiver.class);

    @StreamListener(SinkSender.JAY)
    public void receive(User payload){
        log.info("Receiver : {}",payload);

    }

    @StreamListener(SinkSender.YQ)
    public void receiveYQ(User payload){
        log.info("Receiver : {}",payload);

    }

}
