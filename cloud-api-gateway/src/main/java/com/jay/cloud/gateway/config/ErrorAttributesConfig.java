package com.jay.cloud.gateway.config;

import com.jay.cloud.gateway.exception.CloudErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/11/23.
 */
@Configuration
public class ErrorAttributesConfig {

    @Bean
    public CloudErrorAttributes jsonErrorAttributes(){
        return new CloudErrorAttributes();
    }
}
