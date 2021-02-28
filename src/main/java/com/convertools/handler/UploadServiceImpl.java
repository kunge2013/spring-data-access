package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.convertools.AccessTools;
import com.convertools.entity.Certificate;
import com.convertools.entity.CheckResult;
import com.convertools.entity.ParamFactValue;
import com.convertools.entity.UpData;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fangkun
 * @date 2020/12/12 16:45
 * @description:
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${result.fieldupset}")
    private List<String> fieldupSet;


    private static Log logger = LogFactory.getLog(UploadServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OkHttpClient.Builder builder;

    /*缓存记录路径*/
    @Value("${temp.file.path}")
    private String filePath;


    @Value("${upload.initall}")
    private boolean uploadinitall = false;

    @Value("${upload.host}")
    private String host;

    @Resource(name = "fieldMapper")
    private HashMap<String, String> fieldMapper;

    @Value("${upload.uploadPath}")
    private String uploadPath = "api/v1/code";


    @Value("${data.path.dir}")
    private String dataPathDir;

    @Value("${data.path.user}")
    private String user;

    @Value("${data.path.password}")
    private String password;

    /*设备编号*/
    @Value("${device.no}")
    private String code;

    @Autowired
    private Certificate certificate;


    public Map<Integer, List<ParamFactValue>> transtDataByfileName(String savefilename) {
        Map<String, Object> map = new HashMap<>();
        String filePath = dataPathDir +  savefilename;
        AccessTools tools = new AccessTools();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = tools.getConnection(filePath, user, password);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select TestNo,littleNo,Name,TheValue,Unit,UserOrResultParam from ParamFactValue");
            List<ParamFactValue> paramFactValues = new ArrayList<>();
            while (result.next()) {
                ParamFactValue paramFactValue = new ParamFactValue();
                paramFactValue.setTestNo(result.getInt("TestNo"));
                paramFactValue.setLittleNo(result.getInt("littleNo"));
                paramFactValue.setName(result.getString("Name"));
                paramFactValue.setTheValue(result.getString("TheValue"));
                paramFactValue.setUnit(result.getString("Unit"));
                paramFactValue.setUserOrResultParam(result.getInt("UserOrResultParam"));
                /*存入集合*/
                paramFactValues.add(paramFactValue);
            }
            logger.info("paramFactValues data == " + JSON.toJSONString(paramFactValues));
            /*分组返回 */
            if (!paramFactValues.isEmpty()) {
                return paramFactValues.stream().collect(Collectors.groupingBy(d -> d.getTestNo()));
            }
        } catch (Exception e) {
            logger.error("转换数据失败!!!! ", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return new HashMap<>();
    }



    public UpData convertByParamFactValues(int simpleNo, String fileName, List<ParamFactValue> paramFactValues) {
        Map<String, String> map = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SimpleDateFormat workTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat encoderFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat projectNoFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String dateStr = fileName.substring(0, 19);
        String workTime = "";
        String projectNo = "";
        String encoder = "";
        Date date = null;
        try {
             date = dateFormat.parse(dateStr);
             workTime  = workTimeFormat.format(date);
             encoder  = encoderFormat.format(date);
             projectNo  = projectNoFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!paramFactValues.isEmpty())  {
            logger.info("paramFactValues data ======>>> " + JSON.toJSONString(paramFactValues));
            logger.info("fieldMapper  ======>>> " + JSON.toJSONString(fieldMapper));
            for (ParamFactValue paramFactValue : paramFactValues) {
                String name = paramFactValue.getName().trim().toLowerCase();
                logger.info("name = "  +  name  + "val = " + paramFactValue.getTheValue() + fieldMapper.containsKey(name));
                if (fieldMapper.containsKey(name)) {
                    map.put(fieldMapper.get(name), paramFactValue.getTheValue());
                }
            }
        }
        logger.info("map data ======>>> " + JSON.toJSONString(map));
        String infoStr = JSON.toJSONString(map);
        UpData upData = JSON.parseObject(infoStr, UpData.class);
        upData.setCode(code);
        /*试样编号生成处理*/
//        if( upData.getSampleNo() == null || upData.getSampleNo().isEmpty()) {
//            upData.setSampleNo("第" + simpleNo + "根");
//        }
//        /*项目编号*/
//        if (upData.getProjectNo() == null || upData.getProjectNo().isEmpty()) {
//            upData.setProjectNo(projectNo);
//        }
        /*委托单号*/
//        if (upData.getECorder() == null || upData.getECorder().isEmpty()) {
//            upData.setECorder(encoder);
//        }
        /*委托单号*/
        upData.setWorkTime(workTime);
        upData.setOperators("管理员");
        /*项目编号*/
        return upData;
    }


    public Map<String, Object> convertToMap(int simpleNo, String fileName, List<ParamFactValue> paramFactValues) {
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SimpleDateFormat workTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat encoderFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat projectNoFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String dateStr = fileName.substring(0, 19);
        String workTime = "";
        String projectNo = "";
        String encoder = "";
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
            workTime  = workTimeFormat.format(date);
            encoder  = encoderFormat.format(date);
            projectNo  = projectNoFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!paramFactValues.isEmpty())  {
            logger.info("paramFactValues data ======>>> " + JSON.toJSONString(paramFactValues));
            logger.info("fieldMapper  ======>>> " + JSON.toJSONString(fieldMapper));
            for (ParamFactValue paramFactValue : paramFactValues) {
                String name = paramFactValue.getName().trim().toLowerCase();
                logger.info("name = "  +  name  + "val = " + paramFactValue.getTheValue() + fieldMapper.containsKey(name));

                if (fieldMapper.containsKey(name)) {
                    if (paramFactValue.getUnit() != null && !paramFactValue.getUnit().isEmpty()) {
                        try {
                            map.put(fieldMapper.get(name), (paramFactValue.getTheValue() == null || paramFactValue.getTheValue().isEmpty()) ? 0 : Double.parseDouble(paramFactValue.getTheValue()));
                        } catch (Exception e) {
                            logger.error("转化解析报错 , name = " + name +"val = " + paramFactValue.getTheValue(), e);
                            map.put(fieldMapper.get(name), paramFactValue.getTheValue());
                        }
                    } else {
                        map.put(fieldMapper.get(name), paramFactValue.getTheValue());
                    }

                }
            }
        }
        logger.info("map data ======>>> " + JSON.toJSONString(map));

        map.put("code", "美特斯拉伸机");
        /*试样编号生成处理*/
//        if (!map.containsKey("simpleNo") ||  map.get("simpleNo") == null) {
//            map.put("simpleNo", "第" + simpleNo + "根");
//        }
//
//        if (!map.containsKey("ECorder") || map.get("ECorder") == null) {
//            map.put("ECorder", encoder);
//        }

        map.put("WorkTime", workTime);
        map.put("operators", "管理员");
        /*过滤掉不需要的字段*/
        Set<String> keySet = map.keySet();
        // 最终
        Map<String, Object> filterMap = new HashMap<>();
        for (String fieldKey : keySet) {
            if (fieldupSet.contains(fieldKey.trim())) {
                filterMap.put(fieldKey, map.get(fieldKey));
            }
        }

        for (String key : fieldupSet) {
            if (!filterMap.containsKey(key)) {
                logger.info("key ====>>>" + key);
                filterMap.put(key, 0.0);
            }
        }
        return filterMap;
    }

    public void callHttp(String filename) {
        if (certificate.isInvalid()) {
            logger.info("certificate key 已过期...");
            return;
        }

        OkHttpClient client = builder.build();
        String url = host + uploadPath;
        Map<Integer, List<ParamFactValue>> integerListMap = transtDataByfileName(filename);
        List<Integer> keyList = new ArrayList<>(integerListMap.keySet());
        keyList.sort((a,b) -> {
            if (a > b) return 1;
            if (a < b) return -1;
            else return 0;
        });
        int  i = 0;
        for (Integer integer : keyList) {
            UpData upData = convertByParamFactValues(++ i ,filename, integerListMap.get(integer));
            //check
            CheckResult checkResult = checkUpData(upData);
            if (checkResult != CheckResult.SUCCESS) {
                JOptionPane.showMessageDialog(null, checkResult.desc, "上传数据校验", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            String bodyStr = JSON.toJSONString(upData);
            logger.info("data === " + bodyStr);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                logger.info("isSuccessful = " +  response.isSuccessful() +", 收到消息" + JSON.toJSONString(response)+ "== message " + response.message());
                if (response.isSuccessful() && "Ok".equalsIgnoreCase(response.message())) {
                    logger.info("执行成功  返回结果为 response =" +  JSON.toJSONString(response) +", message =" + response.message());
                } else {
                    logger.error(" .... 接口 调用出错.... " + bodyStr + ", response = " + JSON.toJSONString(response));
                    throw  new RuntimeException("call inf fail ");
                }
                response.close();
            } catch (IOException e) {
                logger.error("outPutData 执行出错.... " + bodyStr, e);
            }
        }
    }


    public void callHttpExt(String filename) {
        if (certificate.isInvalid()) {
            logger.info("certificate key 已过期...");
            return;
        }

        OkHttpClient client = builder.build();
        String url = host + uploadPath;
        Map<Integer, List<ParamFactValue>> integerListMap = transtDataByfileName(filename);
        List<Integer> keyList = new ArrayList<>(integerListMap.keySet());
        keyList.sort((a,b) -> {
            if (a > b) return 1;
            if (a < b) return -1;
            else return 0;
        });
        int  i = 0;
        for (Integer integer : keyList) {
            Map upData = convertToMap(++ i ,filename, integerListMap.get(integer));

            CheckResult checkResult = checkUpDataMap(upData);
            if (checkResult != CheckResult.SUCCESS) {
//                JOptionPane.showMessageDialog(null, checkResult.desc, "上传数据校验", JOptionPane.ERROR_MESSAGE);
                new Thread(() ->  JOptionPane.showMessageDialog(null, checkResult.desc, "上传数据校验", JOptionPane.ERROR_MESSAGE)).start();
                continue;
            }
            String bodyStr = JSON.toJSONString(upData);
            logger.info("data === " + bodyStr);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                logger.info("isSuccessful = " +  response.isSuccessful() +", 收到消息" + JSON.toJSONString(response)+ "== message " + response.message());
                if (response.isSuccessful() && "Ok".equalsIgnoreCase(response.message())) {
                    logger.info("执行成功  返回结果为 response =" +  JSON.toJSONString(response) +", message =" + response.message());
                } else {
                    logger.error(" .... 接口 调用出错.... " + bodyStr + ", response = " + JSON.toJSONString(response));
                    throw  new RuntimeException("call inf fail ");
                }
                response.close();
            } catch (IOException e) {
                logger.error("outPutData 执行出错.... " + bodyStr, e);
            }
        }
    }


    @Override
    public CheckResult checkUpData(UpData upData) {
        if (upData.getECorder() == null || upData.getECorder().isEmpty()) {
            return CheckResult.ECORDER_EMPTY;
        }
        if (upData.getSampleNo() == null || upData.getSampleNo().isEmpty()) {
            return CheckResult.ECORDER_EMPTY;
        }
        return CheckResult.SUCCESS;
    }


    @Override
    public CheckResult checkUpDataMap(Map<String, Object> map) {
        if (!map.containsKey("SampleNo")
                ||  map.get("SampleNo") == null
                || ((String)map.get("SampleNo")).isEmpty()) {
            return CheckResult.SIMPLENO_EMPTY;
        }
        if (!map.containsKey("ECorder")
                || map.get("ECorder") == null
                || ((String)map.get("ECorder")).isEmpty()) {
            return CheckResult.ECORDER_EMPTY;
        }
        return CheckResult.SUCCESS;
    }


    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//        Date date = dateFormat.parse("2020-01-12-16-11-10");
//        System.out.println(date);
        Map<String, String> map = new HashMap<>();
        map.put("设备编号".trim().toLowerCase(),"code");
        map.put("委托单号".trim().toLowerCase(),"ECorder");
        map.put("试样编号".trim().toLowerCase(),"SampleNo");
        map.put("试样直径".trim().toLowerCase(),"do");
        map.put("断后直径".trim().toLowerCase(),"du");
        map.put("原始标距（Lo）".trim().toLowerCase(),"lo");
        map.put("断后标距（Lu）".trim().toLowerCase(),"lu");
        map.put("最大力（Fm）".trim().toLowerCase(),"Fm");
        map.put("最大力（Fm）".trim().toLowerCase(),"Fm");

        map.put("规定塑性延伸力（Fp）".trim().toLowerCase(),"Fp");
        map.put("规定塑性延伸强度（Rp）","Rp0.2");
        map.put("规定非比例延伸强度（Rp）","Rp0.2");


        map.put("断后伸长率（A）".trim().toLowerCase(),"A");
        map.put("断后伸长率（A）".trim().toLowerCase(),"A");


        map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");
        map.put("断面收缩率（Z）".trim().toLowerCase(),"Z");

        map.put("抗拉强度（Rm）".trim().toLowerCase(),"RM");

        map.put("下屈服力（FeL）".trim().toLowerCase(),"Fel");
        map.put("屈服力（FeL）".trim().toLowerCase(),"Fel");

        map.put("上屈服力强度（ReH）".trim().toLowerCase(),"ReH");
        map.put("上屈服力（FeH）".trim().toLowerCase(),"FeH");
        map.put("下屈服强度（ReL）".trim().toLowerCase(),"ReL");
        map.put("屈服强度（ReL）".trim().toLowerCase(),"ReL");


        map.put("规定总延伸力（Ft）".trim().toLowerCase(),"Ft");
        map.put("规定总延伸强度（Rt）".trim().toLowerCase(),"Rt");
        map.put("试验时间".trim().toLowerCase(),"WorkTime");
        map.put("操作员".trim().toLowerCase(),"operators");
        map.put("钢材品种".trim().toLowerCase(),"gcpz");



        /*扩展字段*/
        map.put("试样直径（d）".trim().toLowerCase(),"试样直径（d）");
        map.put("断后直径（du）".trim().toLowerCase(),"断后直径（du）");
        List<ParamFactValue> list = JSON.parseObject("[{\"littleNo\":0,\"name\":\"试样直径（d）\",\"testNo\":2482,\"theValue\":\"10\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":1,\"name\":\"断后直径（du）\",\"testNo\":2482,\"theValue\":\"\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":2,\"name\":\"原始标距（Lo）\",\"testNo\":2482,\"theValue\":\"50\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":3,\"name\":\"断后标距（Lu）\",\"testNo\":2482,\"theValue\":\"\",\"unit\":\"mm\",\"userOrResultParam\":0},{\"littleNo\":4,\"name\":\"委托单号\",\"testNo\":2482,\"theValue\":\"536834\",\"unit\":\"\",\"userOrResultParam\":0},{\"littleNo\":5,\"name\":\"试样编号\",\"testNo\":2482,\"theValue\":\"002\",\"unit\":\"\",\"userOrResultParam\":0},{\"littleNo\":0,\"name\":\"屈服力（FeL）\",\"testNo\":2482,\"theValue\":\"26100.7109\",\"unit\":\"kN\",\"userOrResultParam\":1},{\"littleNo\":1,\"name\":\"屈服强度（ReL）\",\"testNo\":2482,\"theValue\":\"332.3246\",\"unit\":\"MPa\",\"userOrResultParam\":1},{\"littleNo\":2,\"name\":\"规定非比例延伸强度（Rp）\",\"testNo\":2482,\"theValue\":\"\",\"unit\":\"MPa\",\"userOrResultParam\":1},{\"littleNo\":3,\"name\":\"最大力（Fm）\",\"testNo\":2482,\"theValue\":\"38240.7813\",\"unit\":\"kN\",\"userOrResultParam\":1},{\"littleNo\":4,\"name\":\"抗拉强度（Rm）\",\"testNo\":2482,\"theValue\":\"486.8969\",\"unit\":\"MPa\",\"userOrResultParam\":1},{\"littleNo\":5,\"name\":\"断后伸长率（A）\",\"testNo\":2482,\"theValue\":\"\",\"unit\":\"％\",\"userOrResultParam\":1},{\"littleNo\":6,\"name\":\"断面收缩率（Z）\",\"testNo\":2482,\"theValue\":\"\",\"unit\":\"％\",\"userOrResultParam\":1}]\n",
                new TypeReference<List<ParamFactValue>>(){});
        Map<Integer, List<ParamFactValue>> listMap = list.stream().collect(Collectors.groupingBy(d -> d.getTestNo()));
        Map<String, String > dataMap = new HashMap<>();
        listMap.forEach((k , v) -> {
            for (ParamFactValue paramFactValue : v) {
                String name = paramFactValue.getName().trim().toLowerCase();
                /*动态赋值*/
                if (map.containsKey(name)) {
                    dataMap.put(map.get(name), paramFactValue.getTheValue());
                }
            }
            String infoStr = JSON.toJSONString(dataMap);
            UpData upData = JSON.parseObject(infoStr, UpData.class);
            System.out.println(JSON.toJSONString(upData));
        });
    }
}
