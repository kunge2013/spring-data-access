package com.convertools.transdto;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

/**
 * @author fangkun
 * @date 2020/11/13 15:38
 * @description:
 */
@Configuration
@Slf4j
public class FieldConfig {

    @Value("${result.fieldmap}")
    private String filejson;

    @Value("${result.fieldupset}")
    private List<String> fieldupSet;
    @Bean
    public HashMap<String, String> fieldMapper() {
        log.info("filejson ==== {}", filejson);
        HashMap map = JSON.parseObject(filejson, HashMap.class);
        log.info("map ==== {}", map);
        map.put("设备编号".trim().toLowerCase(),"code");

        map.put("委托单号".trim().toLowerCase(),"ECorder");
        map.put("试验项目号".trim().toLowerCase(),"projectNo");
        map.put("试样编号".trim().toLowerCase(),"SampleNo");
        //map.put("试验编号".trim().toLowerCase(),"SampleNo");

        map.put("试样直径".trim().toLowerCase(),"do");
        map.put("断后直径".trim().toLowerCase(),"du");
        map.put("原始标距（Lo）".trim().toLowerCase(),"lo");
        map.put("断后标距（Lu）".trim().toLowerCase(),"lu");
        map.put("最大力（Fm）".trim().toLowerCase(),"Fm");
        map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
        map.put("规定塑性延伸强度（Rp）".trim().toLowerCase(),"RP");
        map.put("断后伸长率（A）".trim().toLowerCase(),"A");
        map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
        map.put("抗拉强度（Rm）".trim().toLowerCase(),"Rm");
        map.put("下屈服力（FeL）".trim().toLowerCase(),"FeL");
        map.put("上屈服力强度（ReH）".trim().toLowerCase(),"ReH");
        map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
        map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
        map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
        map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
        map.put("试验时间".trim().toLowerCase(),"WorkTime");
        map.put("操作员".trim().toLowerCase(),"operators");
        map.put("钢材品种".trim().toLowerCase(),"gcpz");


     /*部分兼容*/
        {
            map.put("最大力（Fm）".trim().toLowerCase(),"Fm");

            map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
            map.put("规定非比例延伸力（Fp）".trim().toLowerCase(),"Fp");
            map.put("规定塑性延伸强度（Rp）".trim().toLowerCase(),"RP");
            map.put("规定非比例延伸强度（Rp）".trim().toLowerCase(),"RP");


            map.put("断后伸长率（A）".trim().toLowerCase(),"A");
            map.put("断后伸长率（A）".trim().toLowerCase(),"A");


            map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
            map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");

            map.put("抗拉强度（Rm）".trim().toLowerCase(),"Rm");

            map.put("下屈服力（FeL）".trim().toLowerCase(),"FeL");
            map.put("屈服力（FeL）".trim().toLowerCase(),"FeL");

            map.put("上屈服力强度（ReH）".trim().toLowerCase(),"ReH");
            map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
            map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
            map.put("屈服强度（ReL）".trim().toLowerCase(),"ReL");


            map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
            map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
            map.put("试验时间".trim().toLowerCase(),"WorkTime");
            map.put("操作员".trim().toLowerCase(),"operators");
            map.put("钢材品种".trim().toLowerCase(),"gcpz");




            map.put("试样直径（d）".trim().toLowerCase(),"do");
            map.put("断后直径（du）".trim().toLowerCase(),"du");
        }

        /**/
        /*方案 快拉棒材 B */
        {
            map.put("试验项目号".trim().toLowerCase(),"ECorder");
            map.put("试样直径（do）".trim().toLowerCase(),"do");

            map.put("委托单号".trim().toLowerCase(),"ECorder");
            map.put("试验项目号".trim().toLowerCase(),"projectNo");
            map.put("试样编号".trim().toLowerCase(),"SampleNo");


            map.put("试样直径".trim().toLowerCase(),"do");
            map.put("断后直径".trim().toLowerCase(),"du");
            map.put("原始标距（Lo）".trim().toLowerCase(),"lo");
            map.put("断后标距（Lu）".trim().toLowerCase(),"lu");
            map.put("最大力（Fm）".trim().toLowerCase(),"Fm");
            map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
            map.put("规定塑性延伸强度（Rp）".trim().toLowerCase(),"RP");
            map.put("断后伸长率（A）".trim().toLowerCase(),"A");
            map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
            map.put("抗拉强度（Rm）".trim().toLowerCase(),"Rm");
            map.put("下屈服力（FeL）".trim().toLowerCase(),"FeL");
            map.put("上屈服强度（ReH）".trim().toLowerCase(),"ReH");

            map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
            map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
            map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
            map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
            map.put("试验时间".trim().toLowerCase(),"WorkTime");
            map.put("操作员".trim().toLowerCase(),"operators");
            map.put("钢材品种".trim().toLowerCase(),"gcpz");
        }

        {
            //            平行长度（Lc）
            map.put("平行长度（Lc）".trim().toLowerCase(), "Lc");
            //            引伸计标距（Le）
            map.put("引伸计标距（Le）".trim().toLowerCase(), "Le");
            //            规定塑性延伸率（ep）
            map.put("规定塑性延伸率（ep）".trim().toLowerCase(), "ep");
            //            规定总延伸率（et）
            map.put("规定总延伸率（et）".trim().toLowerCase(), "et");
            //            试样面积（So）
            map.put("试样面积（So）".trim().toLowerCase(), "So");
            //弹性模量（E）
            map.put("弹性模量（E）".trim().toLowerCase(), "E");
        }
        return map;
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();
        map.put("设备编号".trim().toLowerCase(),"code");

        map.put("委托单号".trim().toLowerCase(),"ECorder");
        map.put("试验项目号".trim().toLowerCase(),"projectNo");
        map.put("试样编号".trim().toLowerCase(),"SampleNo");
        //map.put("试验编号".trim().toLowerCase(),"SampleNo");

        map.put("试样直径".trim().toLowerCase(),"do");
        map.put("断后直径".trim().toLowerCase(),"du");
        map.put("原始标距（Lo）".trim().toLowerCase(),"lo");
        map.put("断后标距（Lu）".trim().toLowerCase(),"lu");
        map.put("最大力（Fm）".trim().toLowerCase(),"Fm");
        map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
        map.put("规定塑性延伸强度（Rp）".trim().toLowerCase(),"RP");
        map.put("断后伸长率（A）".trim().toLowerCase(),"A");
        map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
        map.put("抗拉强度（Rm）".trim().toLowerCase(),"Rm");
        map.put("下屈服力（FeL）".trim().toLowerCase(),"FeL");
        map.put("上屈服力强度（ReH）".trim().toLowerCase(),"ReH");
        map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
        map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
        map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
        map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
        map.put("试验时间".trim().toLowerCase(),"WorkTime");
        map.put("操作员".trim().toLowerCase(),"operators");
        map.put("钢材品种".trim().toLowerCase(),"gcpz");


        /*部分兼容*/
        {
            map.put("最大力（Fm）".trim().toLowerCase(),"Fm");

            map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
            map.put("规定非比例延伸力（Fp）".trim().toLowerCase(),"Fp");
            map.put("规定塑性延伸强度（Rp）".trim().toLowerCase(),"RP");
            map.put("规定非比例延伸强度（Rp）".trim().toLowerCase(),"RP");


            map.put("断后伸长率（A）".trim().toLowerCase(),"A");
            map.put("断后伸长率（A）".trim().toLowerCase(),"A");


            map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
            map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");

            map.put("抗拉强度（Rm）".trim().toLowerCase(),"Rm");

            map.put("下屈服力（FeL）".trim().toLowerCase(),"FeL");
            map.put("屈服力（FeL）".trim().toLowerCase(),"FeL");

            map.put("上屈服力强度（ReH）".trim().toLowerCase(),"ReH");
            map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
            map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
            map.put("屈服强度（ReL）".trim().toLowerCase(),"ReL");


            map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
            map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
            map.put("试验时间".trim().toLowerCase(),"WorkTime");
            map.put("操作员".trim().toLowerCase(),"operators");
            map.put("钢材品种".trim().toLowerCase(),"gcpz");




            map.put("试样直径（d）".trim().toLowerCase(),"do");
            map.put("断后直径（du）".trim().toLowerCase(),"du");
        }

        /**/
        /*方案 快拉棒材 B */
        {
            map.put("试验项目号".trim().toLowerCase(),"ECorder");
            map.put("试样直径（do）".trim().toLowerCase(),"do");

            map.put("委托单号".trim().toLowerCase(),"ECorder");
            map.put("试验项目号".trim().toLowerCase(),"projectNo");
            map.put("试样编号".trim().toLowerCase(),"SampleNo");


            map.put("试样直径".trim().toLowerCase(),"do");
            map.put("断后直径".trim().toLowerCase(),"du");
            map.put("原始标距（Lo）".trim().toLowerCase(),"lo");
            map.put("断后标距（Lu）".trim().toLowerCase(),"lu");
            map.put("最大力（Fm）".trim().toLowerCase(),"Fm");
            map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
            map.put("规定塑性延伸强度（Rp）".trim().toLowerCase(),"RP");
            map.put("断后伸长率（A）".trim().toLowerCase(),"A");
            map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
            map.put("抗拉强度（Rm）".trim().toLowerCase(),"Rm");
            map.put("下屈服力（FeL）".trim().toLowerCase(),"FeL");
            map.put("上屈服强度（ReH）".trim().toLowerCase(),"ReH");

            map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
            map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
            map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
            map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
            map.put("试验时间".trim().toLowerCase(),"WorkTime");
            map.put("操作员".trim().toLowerCase(),"operators");
            map.put("钢材品种".trim().toLowerCase(),"gcpz");
        }
        System.out.println(JSON.toJSONString(map));
    }

}
