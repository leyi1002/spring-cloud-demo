package com.jay.cloud.stream.hello.sender;

import com.jay.cloud.stream.hello.bean.User;
import com.jay.cloud.stream.hello.utils.JackSonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/12/1.
 */
@Controller
public class UserMessageController {

    @Autowired
    private SinkSender sinkSender;

    @ResponseBody
    @RequestMapping("/sendGC")
    public String sendGC(){
        User user =new User();
        user.setAge(20);
        user.setFirstName("辞");
        user.setLastName("李");
        user.setSchool("驿前中学");
        String s = JackSonUtils.toJson(user);
        boolean send = sinkSender.outputGC().send(MessageBuilder.withPayload(s).build());
        if(send){
            return "发送成功：" + s;
        }
        return "发送失败" + s;
    }

    @ResponseBody
    @RequestMapping("/sendInput")
    public String sendInput(){
        User user =new User();
        user.setAge(20);
        user.setFirstName("辞");
        user.setLastName("李");
        user.setSchool("驿前中学");
        String s = JackSonUtils.toJson(user);
        boolean send = sinkSender.outputYQ().send(MessageBuilder.withPayload(s).build());
        if(send){
            return "发送成功：" + s;
        }
        return "发送失败" + s;
    }

}
