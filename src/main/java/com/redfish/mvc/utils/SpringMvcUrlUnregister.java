package com.redfish.mvc.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将指定url从MVC中移除
 */
@Component
public class SpringMvcUrlUnregister {

    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;



    public void unregister(String... urlPaths){
        if (null == urlPaths || urlPaths.length<=0){
            return;
        }


        Map<RequestMappingInfo, HandlerMethod> urlMap = requestMappingHandlerMapping.getHandlerMethods();
        if (CollectionUtils.isEmpty(urlMap)){
            return;
        }


        List<RequestMappingInfo> toBeRemoved = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : urlMap.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            for (String urlPath : urlPaths){
                if (info.getPatternsCondition().getPatterns().contains(urlPath)) {
                    toBeRemoved.add(info);
                }
            }

        }

        // 从映射中移除指定URL的RequestMappingInfo
        for (RequestMappingInfo info : toBeRemoved) {
            requestMappingHandlerMapping.unregisterMapping(info);
        }
    }

}