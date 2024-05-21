package com.redfish.mvc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 根据配置文件，取消指定url注册
 */
@Component
public class SpringMvcUnRegistApplicationRunner implements ApplicationRunner {

    @Resource
    private SpringMvcUrlUnregister springMvcUnregister;

    @Value("#{'${redfish.mvc.utils.urls.remove:}'.split(',')}")
    private String[] removeUrlPaths;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (null != removeUrlPaths && removeUrlPaths.length>0){
            springMvcUnregister.unregister(removeUrlPaths);
        }

    }



}