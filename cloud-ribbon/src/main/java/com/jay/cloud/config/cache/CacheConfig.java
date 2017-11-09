package com.jay.cloud.config.cache;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/11/8.
 */
@Configuration
public class CacheConfig {

    @Bean
    public FilterRegistrationBean indexFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new CacheFilter());
        registration.addUrlPatterns("/*");
        return registration;
    }
}
