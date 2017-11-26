package com.jay.cloud.config.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/26.
 */
@RefreshScope
@RestController
public class ConfigController {

    private static final Logger log = LoggerFactory.getLogger(ConfigController.class);

    //第一种方式获取from属性
    @Value("${from}")
    private String from;
    //第二种方式获取from属性
    @Autowired
    private Environment env;


    @RequestMapping("/from")
    public String  from(){
        String property = env.getProperty("from", "undifined");
        log.info("env from :{}",property);
        log.info("value from:{}",this.from);
        return this.from;
    }

}
