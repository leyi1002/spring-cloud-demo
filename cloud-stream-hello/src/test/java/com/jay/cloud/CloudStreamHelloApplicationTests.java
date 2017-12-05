package com.jay.cloud;

import com.jay.cloud.stream.hello.sender.SinkSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudStreamHelloApplicationTests {

	@Autowired
	private SinkSender sinkSender;


	@Test
	public void contextLoads() {
		sinkSender.outputGC().send(MessageBuilder.withPayload("From SinkSender").build());
	}


}
