package com.jay.cloud.gateway.controller;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/11/22.
 */
@RestController
public class LocalController {

    @RequestMapping(value = "/local/hello")
    public String localhello(){
        return "gateway hello world";
    }

}
