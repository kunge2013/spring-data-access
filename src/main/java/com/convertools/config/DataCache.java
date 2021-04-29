package com.convertools.config;

import com.convertools.entity.CusIntIOTEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fangkun
 * @date 2021/4/24 21:03
 * @description:
 */
@Slf4j
public class DataCache {

    public static Map<String, Map<String, List<CusIntIOTEntity>>> dataMap = new HashMap<>();
    public static Map<String, Long> dataMapTimeStamp = new HashMap<>();

    // 校验数据是否重复  重复返回true
    public static boolean contains(String fileName, List<CusIntIOTEntity>  currentValues) {
        Map<String, List<CusIntIOTEntity>> docGroup = currentValues.stream().collect(Collectors.groupingBy(CusIntIOTEntity::getDocNo));
        boolean exist = true;
        if(dataMap.containsKey(fileName)) {
            Map<String, List<CusIntIOTEntity>> docMap = dataMap.get(fileName);
            for (Map.Entry<String, List<CusIntIOTEntity>> stringListEntry : docGroup.entrySet()) {
                String docNo = stringListEntry.getKey();
                // docNo 值
                List<CusIntIOTEntity> valueList = stringListEntry.getValue();
                // docNo 结果
                List<CusIntIOTEntity> list = docMap.get(docNo);
                for (CusIntIOTEntity cusIntIOTEntity : valueList) {
                    if (!list.contains(cusIntIOTEntity)) {
                        exist = false;
                        break;
                    }
                }
            }
        }
        // 保存到缓存
        if (!exist) {
            for (String docNo : docGroup.keySet()) {
                dataMap.put(fileName, docGroup);
            }
        }
        // 更新缓存数据
        dataMapTimeStamp.put(fileName, System.currentTimeMillis());
        return exist;
    }


}
