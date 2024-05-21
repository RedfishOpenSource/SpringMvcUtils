package com.redfish.mvc.utils;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.env.Environment;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * 如果@RequestMapping等注解修饰的方法上添加@ConditionalOnExpression,则根据@ConditionalOnExpression的value的value判断该url是否生效。
 */
public class RedfishRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private Environment env;
    public RedfishRequestMappingHandlerMapping(Environment env) {
        this.env = env;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {

        ConditionalOnExpression conditionAnnotation = AnnotatedElementUtils
                .findMergedAnnotation(method, ConditionalOnExpression.class);
        if (conditionAnnotation == null) {
            return super.getMappingForMethod(method, handlerType);
        }


        SpelExpressionParser parser = new SpelExpressionParser();
        String el = env.resolvePlaceholders(conditionAnnotation.value());

        boolean enabled = Boolean.TRUE.equals(parser.parseExpression(el).getValue(Boolean.TYPE));
        if (!enabled) {
            return null;
        }

        return super.getMappingForMethod(method, handlerType);
    }


}
