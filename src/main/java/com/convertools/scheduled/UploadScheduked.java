package com.convertools.scheduled;

import com.convertools.handler.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fangkun
 * @date 2020/12/12 20:56
 * @description:
 */
//@Component
@Slf4j
public class UploadScheduked {
    @Autowired
    private UploadService uploadService;

    public static transient final ConcurrentHashMap<String, String> fileChangeMap = new ConcurrentHashMap(16);

    public void pushFile(String fileName, String operator) {
        fileChangeMap.put(fileName, operator);
    }

    @Scheduled(cron = "${check.cron}")
    public void dealChangeFiles() {
        try {
            ConcurrentHashMap.KeySetView<String, String> keys = fileChangeMap.keySet();
            HashSet<String> fileNames = new HashSet<>();
            fileNames.addAll(keys);
            for (String fileName : fileNames) {
                fileChangeMap.remove(fileName);
                uploadService.callHttp(fileName);
            }
        } catch (Exception e) {
            log.error("定时任务执行失败 {}", e);
        } finally {

        }
    }
}
