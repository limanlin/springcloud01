package cn.tedu.sp11.filter;

import cn.tedu.web.util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;



@Component
public class AccessFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 6;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String r = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);
//        return "item-service".equals(r);
        return r.equals("item-service");
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(JsonResult.NOT_LOGIN);
            ctx.setResponseBody(JsonResult.err().code(JsonResult.NOT_LOGIN).msg("not login").toString());
        }
        return null;
    }
}
