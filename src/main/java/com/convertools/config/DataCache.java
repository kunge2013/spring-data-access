package com.convertools.config;

import com.convertools.entity.CusIntIOTEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangkun
 * @date 2021/4/24 21:03
 * @description:
 */
@Slf4j
public class DataCache {

    public static Map<String, List<CusIntIOTEntity>> dataMap = new HashMap<>();
    public static Map<String, Long> dataMapTimeStamp = new HashMap<>();

    // 校验数据是否重复  重复返回true
    public static boolean contains(String fileName, List<CusIntIOTEntity>  currentValues) {
        if(dataMap.containsKey(fileName)) {
            List<CusIntIOTEntity> list = dataMap.get(fileName);
            for (CusIntIOTEntity cusIntIOTEntity : currentValues) {
                if (!list.contains(cusIntIOTEntity)) {
                    // 更新缓存数据
                    dataMap.put(fileName, currentValues);
                    dataMapTimeStamp.put(fileName, System.currentTimeMillis());
                    // 数据不重复 刷新缓存
                    return false;
                }
            }
            return true;
        }
        // 更新缓存数据
        dataMap.put(fileName, currentValues);
        dataMapTimeStamp.put(fileName, System.currentTimeMillis());
        return false;
    }


}
