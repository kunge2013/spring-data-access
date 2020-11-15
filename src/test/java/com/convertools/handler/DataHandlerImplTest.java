package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.convertools.boot.Application;
import com.convertools.entity.OutPutData;
import com.convertools.entity.ParamFactValue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author fangkun
 * @date 2020/11/12 10:51
 * @description:
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class DataHandlerImplTest {

    @Autowired
    private DataHandlerImpl handler;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "fieldMapper")
    private HashMap<String, String> fieldMapper;
    /**
     * 查询最大id
     */
    @Test
    public void testQuerymaxId() {
        int maxId = jdbcTemplate.queryForObject("select max(Id) from outputdata", Integer.class);
        System.out.println(maxId);
    }

    @Test
    public void testQueryObj() {
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select * from outputdata where id = ?", new Object[]{100});
        System.out.println(stringObjectMap);
        OutPutData outPutData = OutPutData.createOutPutData(stringObjectMap);
        List<OutPutData> query = jdbcTemplate.query("select * from outputdata where id = ?", new Object[]{100}, new RowMapper<OutPutData>() {
            @Override
            public OutPutData mapRow(ResultSet rs, int rowNum) throws SQLException {
                OutPutData data = new OutPutData();
                return data;
            }
        });
        System.out.println(query);
    }


    @Test
    public void testQueryList() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from outputdata where id >= ?", new Object[]{10});
        System.out.println(maps);
    }

    @Test
    public void testhander() {
        handler.handler();
    }
    private OutPutData coverData(Map<String , Object> dataMap) {

        Set<String> fields = dataMap.keySet();

//        "Id" -> {Integer@5939} 100
//        "TestNo" -> "jk-ye2011(12)00012-20"
//        "TestItem" -> "1"
//        "ItemTable" -> null
//        "SampleNo" -> "226866"
//        "OperatorName" -> "管理员"
//        "CurOrder" -> {Integer@5950} 3
//        "TestCount" -> {Integer@5950} 3
//        "Temperature" -> {BigDecimal@5953} "0E-7"
//        "Humidity" -> {BigDecimal@5953} "0E-7"
//        "TestTime" -> {Timestamp@5956} "2020-11-04 15:47:55.0"
//        "FinalState" -> null
//        "BendResult" -> null
//        "MotherLength" -> {BigDecimal@5953} "0E-7"
//        "MotherWeight" -> {BigDecimal@5953} "0E-7"
//        "OrgGaugeLength" -> {BigDecimal@5962} "100.0000000"
//        "ExtGaugeLength" -> {Integer@5964} 0
//        "Dia" -> {BigDecimal@5953} "0E-7"
//        "Span" -> {BigDecimal@5953} "0E-7"
//        "Length" -> {BigDecimal@5953} "0E-7"
//        "Width" -> {BigDecimal@5969} "150.0000000"
//        "Thickness" -> {BigDecimal@5971} "150.0000000"
//        "Border" -> {BigDecimal@5953} "0E-7"
//        "OutDia" -> {BigDecimal@5953} "0E-7"
//        "InnerDia" -> {BigDecimal@5953} "0E-7"
//        "Area" -> {BigDecimal@5976} "22500.0000000"
//        "EquipCode" -> null
//        "MeasureRange" -> {Integer@5964} 0
//        "Identifier" -> "2"
//        "Category" -> null
//        "IsFinished" -> {Integer@5983} 1
//        "TestID" -> {Integer@5985} 16
//        "SaveFileName" -> "1--jk-ye2011(12)00012-20.mdb"
//        "CtrlMode" -> null
//        "FinalPosition" -> null
//        "MaxLoad" -> {Double@5991} 203.3
//        "MaxDistort" -> {Double@5993} 0.0
//        "MaxStrength" -> {Double@5995} 10.0
//        "YieldUpLoad" -> {Double@5997} 112.5
//        "YieldUpStrength" -> {Double@5999} 5.0
//        "YieldLoad" -> {Double@6001} 111.6
//        "YieldStrength" -> {Double@6003} 5.0
//        "FpLoad" -> {Double@6005} 0.0
//        "FpStrength" -> {Double@6007} 0.0
//        "FtLoad" -> {Double@6009} 0.0
//        "FtStrength" -> {Double@6011} 0.0
//        "FinalLength" -> {Double@6013} 0.0
//        "FinalRate" -> {Double@6015} 0.0
//        "FinalShrink" -> {Double@6017} 100.0
//        "FinalDia" -> {Double@6019} 0.0
//        "FinalWidth" -> {Double@6021} 1.0
//        "FinalThick" -> {Double@6023} 1.0
//        "FinalBorder" -> {Double@6025} 0.0
//        "Elasticity" -> {Double@6027} 0.0
//        "Duration" -> {Double@6029} 121.6
//        "MaxSpeed" -> {Double@6031} 4.48
//        "DistanceBeforeTest" -> null
//        "DistanceAfterTest" -> null
//        "SampleInfo1" -> null
//        "SampleInfo2" -> null
//        "SampleInfo3" -> null
//        "SampleInfo4" -> null
//        "SampleInfo5" -> null
//        "SampleInfo6" -> null
//        "SampleInfo7" -> null
//        "SampleInfo8" -> null
//        "SampleInfo9" -> null
//        "SampleInfo10" -> null
//        "CurvePicture" -> {byte[315]@6045}

        return null;
    }

    @Test
    public void testData() {
        String data ="[{\"littleNo\":0,\"name\":\"钢材品种\",\"testNo\":27,\"theValue\":\"碳钢\",\"unit\":\"\",\"userOrResultParam\":0},{\"littleNo\":1,\"name\":\"试验项目号\",\"testNo\":27,\"theValue\":\"1\",\"unit\":\"\",\"userOrResultParam\":0},{\"littleNo\":2,\"name\":\"试验编号\",\"testNo\":27,\"theValue\":\"jk-ye2011(12)00012-7\",\"unit\":\"\",\"userOrResultParam\":0},{\"littleNo\":3,\"name\":\"试样编号\",\"testNo\":27,\"theValue\":\"243931\",\"unit\":\"\",\"userOrResultParam\":0},{\"littleNo\":4,\"name\":\"试样厚度（ao）\",\"testNo\":27,\"theValue\":\"150\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":5,\"name\":\"试样宽度（bo）\",\"testNo\":27,\"theValue\":\"150\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":6,\"name\":\"断后宽度（bu）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":7,\"name\":\"断后厚度（au）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":8,\"name\":\"平行长度（Lc）\",\"testNo\":27,\"theValue\":\"200\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":9,\"name\":\"原始标距（Lo）\",\"testNo\":27,\"theValue\":\"100\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":10,\"name\":\"断后标距（Lu）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":11,\"name\":\"YPE最大斜率长度\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"cm\",\"userOrResultParam\":0},{\"littleNo\":0,\"name\":\"试样面积（So）\",\"testNo\":27,\"theValue\":\"22500.00\",\"unit\":\"mm^2\",\"userOrResultParam\":1},{\"littleNo\":1,\"name\":\"最大力（Fm）\",\"testNo\":27,\"theValue\":\"203264.8594\",\"unit\":\"kN\",\"userOrResultParam\":1},{\"littleNo\":2,\"name\":\"上屈服力（FeH）\",\"testNo\":27,\"theValue\":\"112486.7656\",\"unit\":\"kN\",\"userOrResultParam\":1},{\"littleNo\":3,\"name\":\"下屈服力（FeL）\",\"testNo\":27,\"theValue\":\"111615.5938\",\"unit\":\"kN\",\"userOrResultParam\":1},{\"littleNo\":4,\"name\":\"下屈服强度（ReL）\",\"testNo\":27,\"theValue\":\"4.9607\",\"unit\":\"MPa\",\"userOrResultParam\":1},{\"littleNo\":5,\"name\":\"上屈服强度（ReH）\",\"testNo\":27,\"theValue\":\"4.9994\",\"unit\":\"MPa\",\"userOrResultParam\":1},{\"littleNo\":6,\"name\":\"抗拉强度（Rm）\",\"testNo\":27,\"theValue\":\"9.034\",\"unit\":\"MPa\",\"userOrResultParam\":1},{\"littleNo\":7,\"name\":\"断面收缩率（Z）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"％\",\"userOrResultParam\":1},{\"littleNo\":8,\"name\":\"断后伸长率（A）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"％\",\"userOrResultParam\":1},{\"littleNo\":9,\"name\":\"试验时间\",\"testNo\":27,\"theValue\":\"2020-11-15 17:27:20\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":10,\"name\":\"断后伸长\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"cm\",\"userOrResultParam\":1},{\"littleNo\":11,\"name\":\"最大力总延伸（△Lm）\",\"testNo\":27,\"theValue\":\"2.0426\",\"unit\":\"cm\",\"userOrResultParam\":1},{\"littleNo\":12,\"name\":\"弹性模量（E）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":13,\"name\":\"弹性段斜率（mE）\",\"testNo\":27,\"theValue\":\"39038.11\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":14,\"name\":\"断后最小横截面积（Su）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"mm^2\",\"userOrResultParam\":1},{\"littleNo\":15,\"name\":\"断裂总延伸（△Lf）\",\"testNo\":27,\"theValue\":\"-0.0004\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":16,\"name\":\"断裂总延伸率（At）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":17,\"name\":\"断裂能量\",\"testNo\":27,\"theValue\":\"4933.5614\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":18,\"name\":\"最大力塑性延伸率（Ag）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":19,\"name\":\"最大力总延伸率（Agt）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":20,\"name\":\"规定塑性延伸力（Fp）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":21,\"name\":\"规定塑性延伸力（滞后环）（Fp）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":22,\"name\":\"规定塑性延伸力（逐步逼近）（Fp）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":23,\"name\":\"规定塑性延伸强度（Rp）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":24,\"name\":\"规定塑性延伸强度（滞后环）（Rp）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":25,\"name\":\"规定塑性延伸强度（逐步逼近）（Rp）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":26,\"name\":\"规定总延伸力（Ft）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":27,\"name\":\"规定总延伸强度（Rt）\",\"testNo\":27,\"theValue\":\"\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":28,\"name\":\"规定残余延伸力（Fr）\",\"testNo\":27,\"theValue\":\"608.7161\",\"unit\":\"\",\"userOrResultParam\":1},{\"littleNo\":29,\"name\":\"规定残余延伸强度（Rr）\",\"testNo\":27,\"theValue\":\"0.0271\",\"unit\":\"\",\"userOrResultParam\":1}]";
        List<ParamFactValue> paramFactValues = JSON.parseObject(data, new TypeReference<List<ParamFactValue>>() {

        });
        System.out.println(paramFactValues);

        for (ParamFactValue paramFactValue : paramFactValues) {
            String name = paramFactValue.getName();
            if(fieldMapper.containsKey(name)) {
                System.out.println("normal = " + name);
            }

            if(fieldMapper.containsKey(name.trim().toLowerCase())) {
                System.out.println("n = " + name);
            }
        }

    }
}