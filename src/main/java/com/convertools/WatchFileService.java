package com.convertools;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;

/**
 * @author fangkun
 * @date 2020/11/4 16:16
 * @description:
 */
@Service
public class WatchFileService implements InitializingBean, AutoCloseable{


    private WatchService watchService;

    @Value("${filepath}")
    private String filepath;

    private Thread watchThread;
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            watchService = FileSystems.getDefault().newWatchService();
            Paths.get(filepath).register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            watchThread = new Thread(() -> startWatch());
            watchThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void startWatch() {

        while (true) {
            try {
                WatchKey take = watchService.take();
                for (WatchEvent<?> event : take.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    System.out.println("kind.name(): " + kind.name());
                    Path path = (Path) take.watchable();
                    System.out.println("path.toString(): " + path.toString());
                    Path filename = (Path) event.context();
                    System.out.println("filename: " + filename);
                }
                // 重设WatchKey
                boolean reset = take.reset();
                System.out.println("key reset:" + reset);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void close() throws Exception {
        watchThread.destroy();
        watchService.close();
    }
}
