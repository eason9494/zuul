package com.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class FilterConfig extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(FilterConfig.class);

    @Override
    public String filterType() {
        return "per";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        //获取请求对象
        RequestContext ctx = RequestContext.getCurrentContext();
        Object pass = ctx.getRequest().getParameter("accessToken");
        if(pass == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}
        }
        logger.info("准入许可 {}",pass);
        return null;
    }
}
