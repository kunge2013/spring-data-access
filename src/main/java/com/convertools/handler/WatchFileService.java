package com.convertools.handler;

import com.alibaba.fastjson.JSON;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

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


    @Value("${use.uploadext}")
    private boolean useUploadExt;

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
//                if(certificate.isInvalid()) {
//                    log.error("证书已过期!!!!!!!!!!!!!!");
//                    System.exit(0);
//                }
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
                        File file = new File(path.toString() + File.separator + fname);
                        long time = file.lastModified();
                        /*只更新20分钟的数据*/
                        if(System.currentTimeMillis()  - time < 1000l * 60 * 3) {
                            operatorMap.put(fname, kind.name());
                        }
                    }
                }
                // 上传数据
                // 重设WatchKey
                boolean reset = take.reset();
                log.error( "reset status", reset);
                log.info("operatorMap {}", JSON.toJSONString(operatorMap));
                if (!operatorMap.isEmpty()) {
                   Set<String> files = new HashSet<>(operatorMap.keySet());
                   log.info("files === >>>>> ", JSON.toJSONString(files));
                   /*清空记录*/
                   operatorMap.clear();
                   for (String fileName: files) {
                        try {
                            if (useUploadExt) {
                                service.callHttpExt(fileName);
                            } else {
                                service.callHttp(fileName);
                            }
                        } catch (Exception e) {
                            log.error( "callHttp  error retry 1{}", fileName, e);
                            try {
                                Thread.sleep(1000l * 30);
                                if (useUploadExt) {
                                    service.callHttpExt(fileName);
                                } else {
                                    service.callHttp(fileName);
                                }

                            } catch (Exception e1) {
                                log.error( "callHttp  error continue ....", fileName, e1);
                            }
                      }
                    }
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
