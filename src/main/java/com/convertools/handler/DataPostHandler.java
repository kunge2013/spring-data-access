package com.convertools.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author fangkun
 * @date 2021/4/24 20:35
 * @description:
 */
@Component
public class DataPostHandler {

    @Autowired
    ApplicationContext context;

    @Value("${exec:false}")
    private boolean execPostApi;

    void handler(String fileName) {
        Map<String, UploadService> beansOfType = context.getBeansOfType(UploadService.class);
        Set<Map.Entry<String, UploadService>> entries = beansOfType.entrySet();
        for (Map.Entry<String, UploadService> entry : entries) {
            String key = entry.getKey();
            if ("uploadServiceImpl".equalsIgnoreCase(key)) {
                entry.getValue().callHttpExt(fileName);
            }
            if (!"uploadServiceImpl".equalsIgnoreCase(key) && execPostApi) {
                entry.getValue().callHttpExt(fileName);
            }
        }
    }
}
