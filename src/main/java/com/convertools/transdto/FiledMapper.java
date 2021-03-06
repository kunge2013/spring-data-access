package com.convertools.transdto;

import com.alibaba.fastjson.JSON;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fangkun
 * @date 2020/11/13 14:30
 * @description:
 */
public class FiledMapper {


    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("设备编号".trim().toLowerCase(),"code");
        map.put("委托单号".trim().toLowerCase(),"ECorder");
        map.put("试样编号".trim().toLowerCase(),"SampleNo");
        map.put("试样直径".trim().toLowerCase(),"do");
        map.put("断后直径".trim().toLowerCase(),"du");
        map.put("原始标距（Lo）".trim().toLowerCase(),"lo");
        map.put("断后标距（Lu）".trim().toLowerCase(),"lu");
        map.put("最大力（Fm）".trim().toLowerCase(),"Fm");
        map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
        map.put("规定塑性延伸强度（Rp）","Rp0.2");
        map.put("断后伸长率（A）".trim().toLowerCase(),"A");
        map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
        map.put("抗拉强度（Rm）".trim().toLowerCase(),"RM");
        map.put("下屈服力（FeL）".trim().toLowerCase(),"Fel");
        map.put("上屈服力强度（ReH）".trim().toLowerCase(),"ReH");
        map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
        map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
        map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
        map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
        map.put("试验时间".trim().toLowerCase(),"WorkTime");
        map.put("操作员".trim().toLowerCase(),"operators");
        map.put("钢材品种".trim().toLowerCase(),"gcpz");
        System.out.println(Base64.getEncoder().encodeToString(JSON.toJSONString(map).getBytes()));
    }
}
