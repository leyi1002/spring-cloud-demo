package com.jay.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/11/21.
 */
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

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
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("send {} request to {}",request.getMethod(),
                request.getRequestURL().toString());
        String accessToken = request.getParameter("accessToken");
        if(accessToken == null){
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.set("isSuccess",false);
            ctx.setResponseBody("none access authorization");
            return null;
        }
        log.info("access token ok");
        ctx.set("isSuccess",true);
        return null;
    }
}
