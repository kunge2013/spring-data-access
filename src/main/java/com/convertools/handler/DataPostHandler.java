package com.convertools.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fangkun
 * @date 2021/4/24 20:35
 * @description:
 */
@Component
public class DataPostHandler {

    @Autowired
    ApplicationContext context;

    void handler(String fileName) {
        Map<String, UploadService> beansOfType = context.getBeansOfType(UploadService.class);
        for (UploadService service : beansOfType.values()) {
            service.callHttpExt(fileName);
        }
    }
}
