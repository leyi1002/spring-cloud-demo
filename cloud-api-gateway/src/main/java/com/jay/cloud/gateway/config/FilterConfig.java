package com.jay.cloud.gateway.config;

import com.jay.cloud.gateway.filter.AccessFilter;
import com.jay.cloud.gateway.filter.NameFilter;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/11/21.
 */
@Configuration
public class FilterConfig {

    @Bean
    public AccessFilter accessFilter(){
        return new AccessFilter();
    }

    @Bean
    public NameFilter nameFilter(){
        return new NameFilter();
    }

    @Bean
    public PatternServiceRouteMapper patternServiceRouteMapper(){
        return new PatternServiceRouteMapper(
                "(?<name>.+)-(?<version>v.+$)",
                "${version}/${name}");
    }

}
