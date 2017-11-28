package com.jay.cloud.rabbitmq.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/11/28.
 */
@Component
@RabbitListener(queues = "hello")
public class Receiver {

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    @RabbitHandler
    public void process(String hello){
        log.info("Receiver : {}",hello);
    }


}
