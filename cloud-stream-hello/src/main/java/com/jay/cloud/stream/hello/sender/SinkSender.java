package com.jay.cloud.stream.hello.sender;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by Administrator on 2017/11/30.
 */
public interface SinkSender {

    String GC="gc";
    String YQ="yq";
    String JAY="jay";

    @Output(SinkSender.GC)
    MessageChannel outputGC();

    @Output(SinkSender.YQ)
    MessageChannel outputYQ();

    @Input(SinkSender.JAY)
    SubscribableChannel inputJay();
}
