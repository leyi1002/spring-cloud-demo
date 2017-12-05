package com.jay.cloud.stream.hello2.controller;

import com.jay.cloud.stream.hello2.sender.App2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/12/1.
 */
@Controller
public class App2Controller {

    @Autowired
    private App2 app2;

    @ResponseBody
    @RequestMapping("/sendGC")
    public String sendGC() {
        app2.publishMessage("hello app1");
        return "app2  发送成功";
    }


    @ResponseBody
    @RequestMapping("/sendPartition")
    public String sendGC(String content) {
        app2.publishMessage("hello app1 ,content: " + content);
        return "app2  发送成功";
    }

}
