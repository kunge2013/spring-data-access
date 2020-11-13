package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.convertools.entity.OutPutData;
import com.convertools.transdto.ResultDTO;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
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

    @Value("${uploadinitall}")
    private boolean uploadinitall = false;

    @Value("${upload.host}")
    private String host;

    private String uploadPath = "/api/v1/code";

    @Override
    public void handler() {
        String lastContent = "0";
        try {
            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
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
        } catch (IOException e) {
            e.printStackTrace();
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
            String bodyStr = JSON.toJSONString(ResultDTO.transToMap(outPutData));
            Request.Builder builder = new Request.Builder();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), bodyStr);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    logger.info("执行成功  返回结果为 response =" +  response.body().toString() );
                } else {
                    logger.error(" .... 接口 调用出错.... " + bodyStr + ", response = " + JSON.toJSONString(response));
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("outPutData 执行出错.... " + bodyStr);
            }
        }

    }
}