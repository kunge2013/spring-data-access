package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.convertools.AccessTools;
import com.convertools.entity.Certificate;
import com.convertools.entity.OutPutData;
import com.convertools.entity.ParamFactValue;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangkun
 * @date 2020/11/12 10:04
 * @description: 数据处理转换
 */
@Service
public class DataHandlerImpl implements DataHandler {

    private static Log logger = LogFactory.getLog(DataHandlerImpl.class);

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
    @Override
    public void handler() {
        /*过期校验*/
        if (certificate.isInvalid()) {
            logger.info("certificate key 已过期...");
            return;
        }
        String lastContent = "0";
        try {
            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                File parentFile = tempFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdir();
                }
                tempFile.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(tempFile));
            StringBuffer sb = new StringBuffer();
            String text = null;
            while((text = reader.readLine()) != null){
                sb.append(text);
            }
            reader.close();

            String latestid = sb.toString();// 第一行最后一个处理的id
            /*当前最大的id*/
            int maxId = jdbcTemplate.queryForObject("select max(Id) from outputdata", Integer.class);
            if (latestid == null || latestid.isEmpty()) {
                latestid = "0";
                if (!uploadinitall) {
                    latestid = maxId + "";
                }
            }
            /*初始化上传所有*/
            //TODO 调用接口
            callInf(Integer.parseInt(latestid));
            /*更新文件内容*/
            lastContent = ("" + maxId);
            FileOutputStream os = new FileOutputStream(tempFile);
            byte[] bytes = lastContent.getBytes();
            os.write(bytes);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("文件没找到" ,e );
        } catch (IOException e) {
            logger.error("io 异常 " ,e );
        } catch (Exception e) {
            logger.error("异常 了 =====" ,e );
        }
    }

    /**
     * TOTO 调用接口信息
     * @param maxId
     */
    /*调用接口数据*/
    private void callInf(int maxId) {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from outputdata where id >= ?", new Object[] {maxId});
        List<OutPutData> dataSet = new ArrayList<>();
        String url = host + uploadPath;
        if (!maps.isEmpty()) {
            for (Map<String, Object> map : maps) {
                dataSet.add(OutPutData.createOutPutData(map));
            }
        }
        OkHttpClient client = builder.build();
        for (OutPutData outPutData : dataSet) {
            Map<String, Object> map = parseData(outPutData);
            map.put("code", code);//设备编号
            String bodyStr = JSON.toJSONString(map);
            logger.info("params key = " + JSON.toJSONString(map.keySet()));
            Request.Builder builder = new Request.Builder();
            logger.info("data === " + bodyStr);
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && "Ok".equalsIgnoreCase(response.message())) {
                    logger.info("执行成功  返回结果为 response =" +  JSON.toJSONString(response) +", messge =" + response.message());
                } else {
                    logger.error(" .... 接口 调用出错.... " + bodyStr + ", response = " + JSON.toJSONString(response));
                    throw  new RuntimeException("call inf fail ");
                }
            } catch (IOException e) {
                logger.error("outPutData 执行出错.... " + bodyStr, e);
            }
        }

    }

    private Map<String, Object> parseData(OutPutData outPutData) {
        Map<String, Object> map = outPutData.convertToMap();
        String savefilename = outPutData.getSavefilename();
        String filePath = dataPathDir +  savefilename;
        AccessTools tools = new AccessTools();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = tools.getConnection(filePath, user, password);
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select TestNo,littleNo,Name,TheValue,Unit,UserOrResultParam from ParamFactValue where TestNo =" + outPutData.getTestid());
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
            logger.info("paramFactValues data == " + JSON.toJSONString(paramFactValues) + ", fieldMapper ====" + JSON.toJSONString(fieldMapper));
            if (!paramFactValues.isEmpty())  {
                for (ParamFactValue paramFactValue : paramFactValues) {
                    String name = paramFactValue.getName().trim().toLowerCase();
                    /*动态赋值*/
                    if (fieldMapper.containsKey(name)) {
                        map.put(fieldMapper.get(name), paramFactValue.getTheValue());
                    }
                }
            }

        } catch (Exception e) {
            logger.error("转换数据失败!!!! outPutData id = " + outPutData.getId() + ", outPutData = " + JSON.toJSONString(outPutData), e);
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
        return map;
    }
}
