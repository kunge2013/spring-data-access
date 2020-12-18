package com.convertools.transdto;

import com.convertools.handler.DataHandler;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;

/**
 * @author fangkun
 * @date 2020/12/12 12:29
 * @description:
 */
//@Configuration
//@Slf4j
public class WatchScheduled {
    private int maxId;
    /*缓存记录路径*/
    @Value("${temp.file.path}")
    private String filePath;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OkHttpClient.Builder builder;


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
    private DataHandler handler;

    private static Log logger = LogFactory.getLog(WatchScheduled.class);

//    @Scheduled(cron = "${check.cron}")
    public void check() {
        logger.info("开始检测有无变化!!!!!!!!!!!");
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
            logger.info("......  查询网络数据数据 开始 .....");
            String latestid = sb.toString();// 第一行最后一个处理的id
            /*当前最大的id*/
            Integer maxId = jdbcTemplate.queryForObject("select max(Id) from outputdata", Integer.class);
            logger.info("......  查询网络数据数据  结束.....");
            if (maxId  == null) {
                maxId = 0;
            }
            if (latestid == null || latestid.isEmpty()) {
                latestid = "0";
            }
            if (Integer.parseInt(latestid) == maxId) {
                logger.info("没有改变不需要上传!!");
                return ;
            }
            handler.handler();

            logger.info("检测完毕!!!!!!!!!!!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("文件没找到" ,e );
        } catch (IOException e) {
            logger.error("io 异常 " ,e );
        } catch (Exception e) {
            logger.error("异常 了 =====" ,e );
        }
    }
}
