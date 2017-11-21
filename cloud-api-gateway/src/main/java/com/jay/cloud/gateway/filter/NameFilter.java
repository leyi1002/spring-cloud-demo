package com.jay.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/11/21.
 */
public class NameFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(NameFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return (boolean) ctx.get("isSuccess");
    }

    @Override
    public Object run() {
        log.info("NameFilter ok");
        return null;
    }
}
