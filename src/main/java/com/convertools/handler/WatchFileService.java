package com.convertools.handler;

import com.convertools.entity.Certificate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.Lifecycle;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fangkun
 * @date 2020/11/4 16:16
 * @description:
 */
@Slf4j
@Service
public class WatchFileService implements InitializingBean, AutoCloseable {

    private static Log logger = LogFactory.getLog(WatchFileService.class);

    ApplicationContext context;
    private WatchService watchService;

    @Value("${data.path}")
    private String filepath;

    @Autowired
    private UploadService service;



    private Thread watchThread;
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get(filepath).register(watchService,
            //                    StandardWatchEventKinds.ENTRY_CREATE,
            //                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            watchThread = new Thread(() -> startWatch());
            watchThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private Certificate certificate;
    private void startWatch() {
        Map<String, String> operatorMap = new HashMap<>();
        while (true) {
            try {
                if(certificate.isInvalid()) {
                    log.error("证书已过期!!!!!!!!!!!!!!");
                    Thread.currentThread().interrupt();
                }
                WatchKey take = watchService.take();
                for (WatchEvent<?> event : take.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    log.info("kind.name(): " + kind.name());
                    Path path = (Path) take.watchable();
                    log.info("path.toString(): " + path.toString());
                    Path filename = (Path) event.context();
                    log.info("filename: " + filename);
                    String fname = filename.toString();
                    if (fname.endsWith(".mdb") || fname.endsWith(".MDB")) {
                        operatorMap.put(fname, kind.name());
                    }
                }
                // 上传数据
                // 重设WatchKey
                boolean reset = take.reset();
                log.info("重置结果为 {}", reset);
                for (String fileName: operatorMap.keySet()) {
                    service.callHttp(fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("报错了 ========", e);
            }
        }
    }


    @Override
    public void close() throws Exception {
        watchThread.destroy();
        watchService.close();
    }
}
