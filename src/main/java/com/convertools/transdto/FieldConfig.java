package com.convertools.transdto;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;
import java.util.HashMap;

/**
 * @author fangkun
 * @date 2020/11/13 15:38
 * @description:
 */
@Configuration
public class FieldConfig {

    @Value("${result.fieldmap}")
    private String filejson;

    @Bean
    public HashMap<String, String> fieldMapper() {
        filejson = new String(Base64.getDecoder().decode(filejson));
        HashMap map = JSON.parseObject(filejson, HashMap.class);
        return map;
    }

}
