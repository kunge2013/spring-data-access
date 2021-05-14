package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.convertools.entity.MtsUploadRecord;
import com.convertools.repository.MtsUploadRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

import static com.convertools.entity.MtsUploadRecord.UPLOAD_READY;

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

    @Value("${data.exec.type:2}")
    private int serviceType;

    @Autowired
    private MtsUploadRecordRepository mtsUploadRecordRepository;

    void handler(String fileName) {
        // 先存数据库,再上报 post
        List<String> services = new ArrayList<>(HandlerEnum.parse(serviceType));
        Collections.sort(services, (o, t) -> {
            if (o.length() > t.length()) {
                return -1;
            } else if (o.length() < t.length()) {
                return 1;
            } else {
                return 0;
            }
        });
        Map<String, UploadService> beansOfType = context.getBeansOfType(UploadService.class);
        Set<Map.Entry<String, UploadService>> entries = beansOfType.entrySet();
        for (Map.Entry<String, UploadService> entry : entries) {
            String key = entry.getKey();
            if (services.contains(key)) {
                entry.getValue().execData(fileName);
            }
        }
    }


    // 每天12 点定时上报  错误数据
    @Scheduled(cron="0 0 12 * * ?")
    public void retryUploadFailData() {
        Long startTime = System.currentTimeMillis();
        try {
            List<MtsUploadRecord> uploadFailedRecords = mtsUploadRecordRepository.findByUploadStatus(UPLOAD_READY);
            log.info("重传文件列表=======" + (uploadFailedRecords== null ? 0 : uploadFailedRecords.size()) );
            if (!CollectionUtils.isEmpty(uploadFailedRecords)) {
                log.info("重传文件列表=========" + JSON.toJSONString(uploadFailedRecords));
                for (MtsUploadRecord uploadFailedRecord : uploadFailedRecords) {
                   try {
                       handler(uploadFailedRecord.getFileName());
                   } catch (Exception e) {
                     log.error("upload fail", e);
                   }
                }
            }
        } catch (Exception e) {
            log.error("重试上报!", e);
        } finally {
            log.info("定时任务重传执行完毕!耗时" +  (System.currentTimeMillis() - startTime) / 1000l + "s");
        }
    }
}
