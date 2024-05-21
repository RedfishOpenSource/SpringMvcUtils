package com.redfish.mvc.utils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@ConditionalOnExpression(value = "${redfish.mvc.utils.web-mvc-registrations.enable:true}")
@Component
public class RedfishWebMvcRegistrations implements WebMvcRegistrations {

    private Environment env;

    public RedfishWebMvcRegistrations(Environment env) {
        this.env = env;
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new RedfishRequestMappingHandlerMapping(env);
    }
}
