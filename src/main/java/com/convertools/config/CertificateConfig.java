package com.convertools.config;

import com.alibaba.fastjson.JSON;
import com.convertools.entity.Certificate;
import com.convertools.handler.DataHandlerImpl;
import com.convertools.utis.AESUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author fangkun
 * @date 2020/11/13 17:11
 * @description:
 */
@Configuration
public class CertificateConfig {

    private static Log logger = LogFactory.getLog(DataHandlerImpl.class);
    @Value("${certificate.key}")
    private String key;

    @Bean
    public Certificate certificate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String content = AESUtil.decrypt(key);
        Certificate certificate = JSON.parseObject(content, Certificate.class);
        Date date = new Date(certificate.getEndtime());
        String endtime = sdf.format(date);
        logger.info("证书有效期到 :" + endtime);
        return certificate;
    }
}
