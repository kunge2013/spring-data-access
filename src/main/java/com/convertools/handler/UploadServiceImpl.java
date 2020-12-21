package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.convertools.AccessTools;
import com.convertools.entity.Certificate;
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
            for (ParamFactValue paramFactValue : paramFactValues) {
                String name = paramFactValue.getName().trim().toLowerCase();
                /*动态赋值*/
                if (fieldMapper.containsKey(name)) {
                    map.put(fieldMapper.get(name), paramFactValue.getTheValue());
                }
            }
        }
        String infoStr = JSON.toJSONString(map);
        UpData upData = JSON.parseObject(infoStr, UpData.class);
        upData.setCode(code);
        /*试样编号生成处理*/
        if( upData.getSampleNo() == null || upData.getSampleNo().isEmpty()) {
            upData.setSampleNo("第" + simpleNo + "根");
        }
//        /*项目编号*/
//        if (upData.getProjectNo() == null || upData.getProjectNo().isEmpty()) {
//            upData.setProjectNo(projectNo);
//        }
        /*委托单号*/
        if (upData.getECorder() == null || upData.getECorder().isEmpty()) {
            upData.setECorder(encoder);
        }
        /*委托单号*/
        upData.setWorkTime(workTime);
        upData.setOperators("管理员");
        /*项目编号*/
        return upData;
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
            UpData upData = convertByParamFactValues(i++ ,filename, integerListMap.get(integer));
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

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = dateFormat.parse("2020-01-12-16-11-10");
        System.out.println(date);
    }
}
