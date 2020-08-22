package com.convertools.service;

import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MdbService implements IMdbService {

    private static Log logger = LogFactory.getLog(MdbService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OkHttpClient.Builder builder;

    @Override
    public void inputData() {
        OkHttpClient client = builder.build();
        Request.Builder builder = new Request.Builder();
        Request req =  builder.url("https://www.baidu.com").get().addHeader("aa", "1").build();
        Call call = client.newCall(req);
        ResponseBody body;
        try (Response response = call.execute()) {
            body = response.body();
            String string = body.string();
            logger.info("input data resp = " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void outData() {
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from outputdata ");
        logger.info("outData data maps = " + maps);
    }

}
