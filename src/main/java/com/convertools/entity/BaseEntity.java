package com.convertools.entity;

import com.convertools.annotations.ConvertField;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础对象
 * @param <T>
 */
public abstract class BaseEntity {

    public Map<String, Object> convertToMap() {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = this.getClass().getFields();
        if (fields != null) {
            for (Field field : fields) {
                ConvertField annotation = field.getAnnotation(ConvertField.class);
                if (annotation != null  && annotation.name() != null && !annotation.name().isEmpty()) {
                    try {
                        result.put(annotation.name(), field.get(this));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
