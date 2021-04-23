package org.hongxi.whatsmars.boot.sample.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hongxi.whatsmars.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hongxi.whatsmars.boot.sample.web.constants.Constants.WEB_MONITOR_FILTER_ORDER;

/**
 * Created by shenhongxi on 2020/11/12.
 */
@Slf4j
public class MonitorFilter extends OncePerRequestFilter implements OrderedFilter {

    // 针对只有一个uri映射到的method
    // key: methodSign value: uriPattern
    private final Map<String, String> uriPatterns = new HashMap<>(128);
    // 多个uri映射到同一个method  @RequestMapping({"/hi", "/hello"})
    // key: methodSign value: RequestMappingInfo
    private final Map<String, RequestMappingInfo> requestMappings = new HashMap<>();
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER + WEB_MONITOR_FILTER_ORDER;

    private static final String UNKNOWN_URI = "/unknown";

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        this.preLoadAllUris();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String uri = getUriPattern(request);
        log.info("uri: {}", uri);
        filterChain.doFilter(request, response);
        // record monitor data
    }

    private void preLoadAllUris() {
        Map<RequestMappingInfo, HandlerMethod> requestMapInfoHandlerMap = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo info : requestMapInfoHandlerMap.keySet()) {
            PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
            if (patternsRequestCondition == null) {
                continue;
            }
            Set<String> patterns = patternsRequestCondition.getPatterns();
            String methodSignal = requestMapInfoHandlerMap.get(info).getMethod().toGenericString();
            if (patterns.size() == 1) {
                uriPatterns.put(methodSignal, patterns.iterator().next());
            } else {
                requestMappings.put(methodSignal, info);
            }
        }
    }

    private String getUriPattern(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getRequestURI())) {
            return UNKNOWN_URI;
        }
        if (uriPatterns.containsValue(request.getRequestURI())) {
            return request.getRequestURI();
        }
        try {
            HandlerExecutionChain handlerExecutionChain =  requestMappingHandlerMapping.getHandler(request);
            if (handlerExecutionChain == null) {
                return UNKNOWN_URI;
            }
            Object handler = handlerExecutionChain.getHandler();
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                String methodSign = handlerMethod.getMethod().toGenericString();
                String uriPattern = uriPatterns.get(methodSign);
                if (StringUtils.isNotBlank(uriPattern)) {
                    return uriPattern;
                }
                if (requestMappings.containsKey(methodSign)) {
                    RequestMappingInfo mappingInfo = requestMappings.get(methodSign);
                    PatternsRequestCondition patternsRequestCondition = mappingInfo.getPatternsCondition();
                    if (patternsRequestCondition == null) {
                        return UNKNOWN_URI;
                    }
                    List<String> matchingPatterns = patternsRequestCondition.getMatchingPatterns(request.getRequestURI());
                    if (CollectionUtils.isNotEmpty(matchingPatterns)) {
                        return matchingPatterns.get(0);
                    }
                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return UNKNOWN_URI;
    }
}
