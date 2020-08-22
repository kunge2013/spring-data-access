package com.convertools.boot;

import com.convertools.service.IMdbService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = "com.convertools.*")
public class Application {
    private static Log logger = LogFactory.getLog(Application.class);

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        IMdbService service = context.getBean(IMdbService.class);
        Environment environment = context.getBean(Environment.class);
        String exectype = environment.getProperty("exectype", "");
        if (exectype.isEmpty()) {
            logger.error("exectype is empty ...");
            throw new RuntimeException("setting is fail ");
        }
        service.execute(Integer.parseInt(exectype));
    }

}
