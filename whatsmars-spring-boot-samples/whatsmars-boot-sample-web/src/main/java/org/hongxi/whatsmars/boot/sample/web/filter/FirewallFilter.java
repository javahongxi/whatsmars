package org.hongxi.whatsmars.boot.sample.web.filter;

import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.hongxi.whatsmars.boot.sample.web.constants.Constants.WEB_FIREWALL_FILTER_ORDER;

/**
 * Created by shenhongxi on 2020/8/16.
 */
public class FirewallFilter extends OncePerRequestFilter implements OrderedFilter {

    private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER + WEB_FIREWALL_FILTER_ORDER;

    private StrictHttpFirewall firewall;

    @Override
    public int getOrder() {
        return order;
    }

    public FirewallFilter() {
        this.firewall = new StrictHttpFirewall();
        this.firewall.setAllowUrlEncodedSlash(true);
        this.firewall.setAllowBackSlash(true);
        this.firewall.setAllowUrlEncodedPercent(true);
    }

    /**
     * 遇到非法URI请求，防火墙拦截并返回418状态码
     * 非法请求如：/xx/../user/info
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            this.firewall.getFirewalledRequest(request);
            filterChain.doFilter(request, response);
        } catch (RequestRejectedException e) {
            response.sendError(HttpStatus.I_AM_A_TEAPOT.value());
        }
    }
}