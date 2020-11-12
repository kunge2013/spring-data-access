package com.convertools.transdto;

import com.convertools.entity.OutPutData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fangkun
 * @date 2020/11/12 14:17
 * @description:
 */
public class ResultDTO {



//    {   "code":"FM06008",
//            "ECorder":"88",
//            "SampleNo":"77",
//            "do":"1",
//            "du":"2",
//            "lo":"3",
//            "lu":"4",
//
//            "Fm":"5",
//            "Fp":"6",
//            "FP0-2":"7",
//            "A":"8",
//            "Z":"9",
//            "RW":"10",
//            "Fel":"11",
//            "ReH":"12",
//            "FeH":"13",
//            "PP":"14",
//            "Ft":"15",
//            "Rt":"16",
//            "WorkTime":"17",
//            "operators":"张三"
//    }


    public static Map<String , Object> transToMap(OutPutData outPutData) {
         Map<String , Object> dataMap = new HashMap<>();
         //TODO 确认是么字段
        /* "code":"FM06008",*/
        dataMap.put("code", outPutData.getTestno());
        //TODO 确认是么字段
        /* "ECorder":"88",*/
        dataMap.put("ECorder", null);
        dataMap.put("SampleNo", outPutData.getSampleno());
        /*TODO 确定*/
        dataMap.put("do", null);
        /*确认*/
        dataMap.put("du", null);
        /*确认*/
        dataMap.put("lo", null);
        /*确认*/
        dataMap.put("lu", null);

        /*Fm*/
        dataMap.put("Fm", null);

        dataMap.put("Fp", null);

        dataMap.put("FP0-2", null);

        dataMap.put("A", null);


        dataMap.put("Z", null);


        dataMap.put("RW", null);

        dataMap.put("Fel", null);

        dataMap.put("ReH", null);

        dataMap.put("FeH", null);

        dataMap.put("PP", null);

        dataMap.put("Ft", null);

        dataMap.put("Rt", null);


        dataMap.put("WorkTime", null);

        dataMap.put("operators", outPutData.getOperatorname());
        return dataMap;
    }
}
