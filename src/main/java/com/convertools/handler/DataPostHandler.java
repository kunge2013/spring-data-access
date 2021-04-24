package com.convertools.handler;

import com.convertools.config.DataCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author fangkun
 * @date 2021/4/24 20:35
 * @description:
 */
@Component
@Slf4j
public class DataPostHandler {

    @Autowired
    ApplicationContext context;

    @Value("${post.switch:false}")
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


    // 定时删除缓存数据
    @Scheduled(cron="0 */5 * * * ?")
    public void timeRemove() {
        try {
            Long currentTime = System.currentTimeMillis();
            for (Map.Entry<String, Long> stringLongEntry : DataCache.dataMapTimeStamp.entrySet()) {
                Long startTime = stringLongEntry.getValue();
                String fileName = stringLongEntry.getKey();
                Long difTime = currentTime - startTime;
                // 10 分钟删除一次
                if (difTime >= 1000 * 60 * 10) {
                    DataCache.dataMapTimeStamp.remove(fileName);
                    DataCache.dataMap.remove(fileName);
                }
            }
        } catch (Exception e) {
            log.error("删除任务失败!", e);
        }
    }
}
