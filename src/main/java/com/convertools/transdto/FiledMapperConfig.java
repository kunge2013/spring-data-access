package com.convertools.transdto;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author fangkun
 * @date 2021/7/31 10:20
 * @description:
 */
@Configuration
@Slf4j
public class FiledMapperConfig implements InitializingBean {

    @Value("${fieldMapperPath}")
   private String fieldMapperPath;

    private HashMap<String, String> fieldMapper = new HashMap();

    public String get(String desc) {
        return fieldMapper.get(desc);
    }

    public String getOrDefault(String desc, String defaultVal) {
        return fieldMapper.getOrDefault(desc, defaultVal);
    }
    public boolean containsKey(String desc) {
        return fieldMapper.containsKey(desc);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BufferedReader reader = null;
        InputStreamReader inputStreamReader = null;
        FileInputStream fileReader = null;
        try {
            fileReader = new FileInputStream(fieldMapperPath);
            inputStreamReader = new InputStreamReader(fileReader, "utf-8");
            reader = new BufferedReader(inputStreamReader);
            StringBuffer buffer = new StringBuffer();
            String readLineStr = null;
            while (!Objects.isNull(readLineStr = reader.readLine())) {
                buffer.append(readLineStr);
            }
            List<FieldDesc> fieldDescs = JSON.parseArray(buffer.toString(), FieldDesc.class);
            for (FieldDesc fieldDesc : fieldDescs) {
                fieldMapper.put(fieldDesc.getDesc().toLowerCase(), fieldDesc.getField());
            }
            log.info("fields  =" +  JSON.toJSONString(fieldMapper));
        } catch (Exception e) {
            log.error("init fieldMapper error", e);
        }finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static class FieldDesc {
        private String field;
        private String desc;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
