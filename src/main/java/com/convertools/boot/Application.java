package com.convertools.boot;

import com.convertools.swing.SubmitDataFrame;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.convertools.*")

@EnableJpaAuditing
@EnableJpaRepositories(basePackageClasses = com.convertools.repository.CusIntIOTRepository.class)
@EntityScan(basePackageClasses = com.convertools.entity.CusIntIOTEntity.class)
public class Application {

    private static Log logger = LogFactory.getLog(Application.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
        logger.info("..美特斯上报程序启动成功..");
    }

}
