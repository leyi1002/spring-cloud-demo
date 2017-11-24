package com.jay.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;

/**
 * Created by Administrator on 2017/11/23.
 */
public class InfoPostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        dosomething();
        return null;
    }

    private void dosomething() {
        throw new RuntimeException("infopost filter error");
    }
}
