package xws.team16.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import xws.team16.zuul.client.SecurityClient;
import xws.team16.zuul.dto.RoleDTO;
import xws.team16.zuul.dto.TokenDTO;

import javax.servlet.http.HttpServletRequest;

@Component @Slf4j
public class AuthFilter extends ZuulFilter {
    @Autowired
    private SecurityClient securityClient;

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

    private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if (request.getHeader("AuthToken") == null) {
            return null;
        }
        String token = request.getHeader("AuthToken").substring(7);

        log.info("Auth filter - sending token");
        try {
            RoleDTO roleDTO = this.securityClient.verify(token);
            StringBuilder roles = new StringBuilder();
            StringBuilder privileges = new StringBuilder();
            for (String role : roleDTO.getRoles())
                roles.append(role).append("-");

            ctx.addZuulRequestHeader("Username", roleDTO.getUsername());
            ctx.addZuulRequestHeader("Roles", String.valueOf(roles));

        } catch (FeignException.NotFound e) {
            setFailedRequest("User does not exist", 403);
        }
        log.info("Auth filter - finished");
        return null;
    }
}
