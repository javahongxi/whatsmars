package org.hongxi.whatsmars.boot.sample.web.filter;

import org.apache.commons.lang.StringUtils;
import org.hongxi.whatsmars.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
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

import static org.hongxi.whatsmars.boot.sample.web.constants.Constants.WEB_URI_FILTER_ORDER;

/**
 * Created by shenhongxi on 2020/11/12.
 */
public class UriFilter extends OncePerRequestFilter implements OrderedFilter {

    // 针对只有一个uri映射到的method
    private final Map<String, String> methodUriMap = new HashMap<>(128);
    // 多个uri映射到同一个method  @RequestMapping({"/hi", "/hello"})
    private final Map<String, RequestMappingInfo> methodReqInfoMap = new HashMap<>();
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    private int order = REQUEST_WRAPPER_FILTER_MAX_ORDER + WEB_URI_FILTER_ORDER;

    private static final String UNKNOWN_URI = "/unknown";

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
        this.preLoadAllUris();
        System.out.println(methodUriMap);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String methodName = getMethodName(request);
        System.out.println(methodName);
        filterChain.doFilter(request, response);
    }

    private void preLoadAllUris() {
        Map<RequestMappingInfo, HandlerMethod> requestMapInfoHandlerMap = requestMappingHandlerMapping.getHandlerMethods();
        for (RequestMappingInfo info : requestMapInfoHandlerMap.keySet()) {
            Set<String> patterns = info.getPatternsCondition().getPatterns();
            String methodSignal = requestMapInfoHandlerMap.get(info).getMethod().toGenericString();
            if (patterns.size() == 1) {
                methodUriMap.put(methodSignal, patterns.iterator().next());
            } else {
                methodReqInfoMap.put(methodSignal, info);
            }
        }
    }

    private String getMethodName(HttpServletRequest request) {
        try {
            HandlerExecutionChain handlerExecutionChain =  requestMappingHandlerMapping.getHandler(request);
            Object objHandler = handlerExecutionChain.getHandler();
            if (objHandler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod)objHandler;
                String methodSign = handlerMethod.getMethod().toGenericString();
                String storedMethod = methodUriMap.get(methodSign);
                if (StringUtils.isNotBlank(storedMethod)) {
                    return storedMethod;
                } else if(methodReqInfoMap.containsKey(methodSign)) {
                    RequestMappingInfo mappingInfo = methodReqInfoMap.get(methodSign);
                    final String reqUri = request.getRequestURI();
                    if (reqUri != null) {
                        List<String> matchedMethods = mappingInfo.getPatternsCondition().getMatchingPatterns(reqUri);
                        if (CollectionUtils.isNotEmpty(matchedMethods)) {
                            return matchedMethods.get(0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return UNKNOWN_URI;
    }
}
