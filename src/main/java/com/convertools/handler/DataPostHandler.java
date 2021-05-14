package com.convertools.handler;

import com.convertools.entity.MtsUploadRecord;
import com.convertools.repository.MtsUploadRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Set<String> services = HandlerEnum.parse(serviceType);
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
        try {
            Long currentTime = System.currentTimeMillis();
            List<MtsUploadRecord> uploadFailedRecords = mtsUploadRecordRepository.findByUploadStatus(UPLOAD_READY);
            if (!CollectionUtils.isEmpty(uploadFailedRecords)) {
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
        }
    }
}
