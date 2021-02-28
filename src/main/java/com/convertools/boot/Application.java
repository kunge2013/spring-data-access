package com.convertools.boot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.convertools.*")
public class Application {

    private static Log logger = LogFactory.getLog(Application.class);

    public static void main(String[] args) throws Exception {
        System.setProperty("java.awt.headless", "false");
        ApplicationContext context = SpringApplication.run(Application.class, args);
        //        DataHandler handler = context.getBean(DataHandler.class);
        //        logger.info("开始执行..." + System.currentTimeMillis());
        //        handler.handler();
        logger.info("..美特斯上报程序启动成功..");
    }

}
