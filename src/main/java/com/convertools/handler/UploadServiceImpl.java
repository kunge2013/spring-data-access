package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.convertools.AccessTools;
import com.convertools.config.DataCache;
import com.convertools.config.Validator;
import com.convertools.entity.Certificate;
import com.convertools.entity.CusIntIOTEntity;
import com.convertools.entity.ParamFactValue;
import com.convertools.repository.CusIntIOTRepository;
import com.convertools.utis.NumberValidationUtil;
import okhttp3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fangkun
 * @date 2020/12/12 16:45
 * @description:
 */
@Service("uploadServiceImpl")
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


    @Autowired
    private Certificate certificate;


    @Autowired
    private CusIntIOTRepository cusIntIOTRepository;

    @Autowired
    private Validator validator;

    /*设备编号*/
    @Value("${device.no:SHT4605/FM06008}")
    private String code;

    @Value("${device.fileType:MNSR}")
    private String fileType;

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





    public List<CusIntIOTEntity> convertToCusIntIOT(int simpleNo, String fileName, List<ParamFactValue> paramFactValues) {
        List<CusIntIOTEntity> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SimpleDateFormat workTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = fileName.substring(0, 19);
        String workTime = "";
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
            workTime  = workTimeFormat.format(date);
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

        /*试样编号生成处理*/
        if (!map.containsKey("ECorder") || map.get("ECorder") == null) {
            map.put("ECorder", "");
        }

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
        String createdBy = "管理员";
        String testBy = "管理员";
        String docNo = "" + map.getOrDefault("ECorder", map.getOrDefault("ECorder", ""));
        final String sNo = docNo + "-" +  simpleNo;
        String esort = "常温";
        String evaluationResult = null;
        Date dateTime = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        filterMap.forEach((eitem, value) -> {
            if (map.containsKey("ECorder")) {
                list.add(CusIntIOTEntity.FACTORY.create(createdBy,
                        testBy,
                        eitem,
                        "" + value,
                        /*机器编号*/
                        code,
                        sNo,
                        esort,
                        evaluationResult,
                        format.format(dateTime),
                        format.format(dateTime),
                        docNo,
                        fileType
                        ));
            }
        });
        return list;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void callHttpExt(String filename) {
        Map<Integer, List<ParamFactValue>> integerListMap = transtDataByfileName(filename);
        List<Integer> keyList = new ArrayList<>(integerListMap.keySet());
        keyList.sort((a,b) -> {
            if (a > b) return 1;
            if (a < b) return -1;
            else return 0;
        });
        int  i = 0;
        List<CusIntIOTEntity> dataSet = new LinkedList<>();
        for (Integer integer : keyList) {
            dataSet.addAll(convertToCusIntIOT(++i, filename, integerListMap.get(integer)));
        }
        Date dateTime = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<CusIntIOTEntity> savaData = new ArrayList<>();
        if (dataSet.isEmpty()) {
            return;
        }
        try {
            // 先校验后复制
            validator.validateInputEncoder(dataSet);
        } catch (Exception e) {
            logger.error("validateInputEncoder error", e);
        }
        for (CusIntIOTEntity cusIntIOTEntity : dataSet) {
            if ("".equals(cusIntIOTEntity.getValue()) || null == cusIntIOTEntity.getValue()) {
                cusIntIOTEntity.setValue("0");
            }
            if (!NumberValidationUtil.isNum(cusIntIOTEntity.getValue())) {
                continue;
            }
            // 不必要的属性过滤掉
            if("SampleNo".equalsIgnoreCase(cusIntIOTEntity.getEitem())
                    || "ECorder".equalsIgnoreCase(cusIntIOTEntity.getEitem())) {
                continue;
            }

            CusIntIOTEntity obj = cusIntIOTRepository.findByEitemAndSampleNoAndEcoder(
                    cusIntIOTEntity.getEitem(),
                    cusIntIOTEntity.getSampleNo(),
                    cusIntIOTEntity.getEcoder());
            if (obj != null) {
                obj.setValue(cusIntIOTEntity.getValue());
                obj.setTestOn(format.format(dateTime));
                obj.setFileType(fileType);
                savaData.add(obj);
            } else {
                savaData.add(cusIntIOTEntity);
            }
        }
        // 校验并初始化数据信息
        // 数据不重复才进行处理
        if (!DataCache.contains(filename, savaData)) {
            // 批量保存
            cusIntIOTRepository.saveAll(savaData);
            cusIntIOTRepository.flush();
        }
    }

}
