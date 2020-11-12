package com.convertools.handler;

import com.convertools.boot.Application;
import com.convertools.entity.OutPutData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;

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

}