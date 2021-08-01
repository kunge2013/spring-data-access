package com.convertools.convertors;

import com.alibaba.fastjson.JSON;
import com.convertools.AccessTools;
import com.convertools.dto.CusIntIOTSaveDTO;
import com.convertools.entity.CusIntIOTEntity;
import com.convertools.entity.DocNoGen;
import com.convertools.entity.MtsUploadRecord;
import com.convertools.entity.ParamFactValue;
import com.convertools.handler.service.IUpdateMdbDocNo;
import com.convertools.repository.MtsUploadRecordRepository;
import com.convertools.swing.SubmitDataFrame;
import com.convertools.transdto.FiledMapperConfig;
import com.convertools.utis.NumberValidationUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static com.convertools.entity.MtsUploadRecord.UPLOAD_READY;

/**
 * @author fangkun
 * @date 2021/5/13 13:09
 * @description:
 */
@Component("cusIntIOTSaveDTOConverter")
@Slf4j
public class CusIntIOTSaveDTOConverter implements IConverter<String, List<CusIntIOTSaveDTO>> {

    @Value("${data.path.dir}")
    private String dataPathDir;

    @Value("${data.path.user}")
    private String user;

    @Value("${data.path.password}")
    private String password;

    @Value("${db.simplenoprefix:CWLS-%s}")
    private String simpleNoPrefix;

    @Value("${device.fileType:MNSR}")
    private String fileType;

    /*设备编号*/
    @Value("${device.no:SHT4605/FM06008}")
    private String code;

    @Value("${device.esort:常温}")
    private String esort;

    @Value("${use.current.desc:false}")
    private Boolean useCurrentDesc;

//    @Resource(name = "fieldMapper")
//    private HashMap<String, String> fieldMapper;

    @Autowired
    private FiledMapperConfig fieldMapper;

    @Autowired
    private MtsUploadRecordRepository mtsUploadRecordRepository;


    @Autowired
    private IUpdateMdbDocNo updateMdbDocNo;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<CusIntIOTSaveDTO> convert(String fileName) {
        return fetchData(fileName);
    }


    public List<CusIntIOTSaveDTO> fetchData(String savefilename) {
        String filePath = dataPathDir +  savefilename;
        List<CusIntIOTSaveDTO> dataResult = new ArrayList<>();
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
            log.info("paramFactValues data == " + JSON.toJSONString(paramFactValues));
            /*分组返回 */
            if (!paramFactValues.isEmpty()) {
                Map<Integer, List<ParamFactValue>> dataMap = paramFactValues.stream().collect(Collectors.groupingBy(d -> d.getTestNo()));
                Set<Map.Entry<Integer, List<ParamFactValue>>> entries = dataMap.entrySet();
                int keyIndex = 1;
                for (Map.Entry<Integer, List<ParamFactValue>> entry : entries) {
                    String docNo = null;
                    CusIntIOTSaveDTO cusIntIOTSaveDTO = new CusIntIOTSaveDTO();
                    dataResult.add(cusIntIOTSaveDTO);
                    Integer testNo = entry.getKey();
                    List<ParamFactValue> dataSet = entry.getValue();
                    docNo = fetchFromMdbDocNo(dataSet);
                    String simpleNo = String.format(simpleNoPrefix, keyIndex++);
                    MtsUploadRecord mtsUploadRecord = mtsUploadRecordRepository.findByFileNameAndSampleNo(savefilename, simpleNo);

                    // 文件中不存在
                    if(null != mtsUploadRecord) {
                        if (StringUtils.isEmpty(docNo)) {
                            // 旧数据中存在
                            docNo = mtsUploadRecord.getDocNo();
                        }
                    } else {
                        mtsUploadRecord = new MtsUploadRecord();
                        mtsUploadRecord.setCreateTime(dateFormat.format(new Date()));
                    }
                    // 都不存在 就需要弹出输入
                    if (StringUtils.isEmpty(docNo)) {
                        DocNoGen docNoGen = DocNoGen.builder().mdbName(savefilename).build();
                        CountDownLatch countDownLatch = new CountDownLatch(1);
                        new SubmitDataFrame(docNoGen, countDownLatch);
                        countDownLatch.await();
                        // 添加忽略上报逻辑
                        if (docNoGen.isIgnore()) {
                            log.info("文件 + " + savefilename + " , 已经忽略上报！");
                            return Lists.newArrayList();
                        }
                        docNo = docNoGen.getDocNo();
                        // 更新mdb 文件委托单号
                        try {
                            updateMdbDocNo.updateDocNo(connection, testNo, docNo);
                        }catch (Exception e) {
                            log.error("委托单号更新失败", e);
                        }
                    }
                    mtsUploadRecord.setDocNo(docNo);
                    mtsUploadRecord.setSampleNo(simpleNo);
                    mtsUploadRecord.setUpdateTime(dateFormat.format(new Date()));
                    mtsUploadRecord.setUploadStatus(UPLOAD_READY);
                    mtsUploadRecord.setFileName(savefilename);
                    // 记录状态
                    mtsUploadRecordRepository.save(mtsUploadRecord);


                    cusIntIOTSaveDTO.setDocNo(docNo);
                    cusIntIOTSaveDTO.setSimpleNo(simpleNo);
                    String createdBy = "管理员";
                    String testBy = "管理员";
                    String sNo = fetchPOSimpleNo(dataSet);
                    // 先从mdb去试样编号，如果有就用，没有就用自动生成的式样编码
                    sNo = StringUtils.isEmpty(sNo) ? simpleNo : sNo;
                    //String esort = "常温";
                    String evaluationResult = "";
                    Date dateTime = new Date();
                    String createOn =  dateFormat.format(dateTime);
                    String testOn =  dateFormat.format(dateTime);

                    // 转化成对象
                    for (ParamFactValue paramFactValue : dataSet) {
                        String eitem = paramFactValue.getName();
                        String value  = paramFactValue.getTheValue();
                        // 不需要记录的字段
                        if (matchFilter(eitem)) {
                            continue;
                        }
                        if ((value== null || value.isEmpty())) {
                            value = "0";
                        }
                        if (value.startsWith(".")) {
                            value = "0" + value;
                        }
                        if (NumberValidationUtil.isNum(value)) {
                            cusIntIOTSaveDTO.addCusIntIOTEntity(
                                    CusIntIOTEntity.FACTORY.create(createdBy,
                                            testBy,
                                            /*eitem,*/
                                            /*useCurrentDesc ? eitem : */fieldMapper.getOrDefault(eitem.toLowerCase(), eitem),
                                            value,
                                            /*机器编号*/
                                            code,
                                            sNo,
                                            esort,
                                            evaluationResult,
                                            createOn,
                                            testOn,
                                            docNo,
                                            fileType
                                    ));
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("转换数据失败!!!! ", e);
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
        return dataResult;
    }


    private String fetchPOSimpleNo(List<ParamFactValue> resultSet) {
        List<ParamFactValue> dataSet = resultSet.stream().filter(o -> "试样编号".equalsIgnoreCase(o.getName())).collect(Collectors.toList());
        if (!dataSet.isEmpty()) {
            ParamFactValue docNoParamFactValue = dataSet.get(0);
            return docNoParamFactValue.getTheValue();
        }
        return "";
    }
    /**
     * 获取委托单号
     * @param paramFactValueList
     * @return
     */
    private String fetchFromMdbDocNo(List<ParamFactValue> paramFactValueList) {
        List<ParamFactValue> dataSet = paramFactValueList.stream().filter(o -> "委托单号".equalsIgnoreCase(o.getName())).collect(Collectors.toList());
        String docNo = null;
        if (!dataSet.isEmpty()) {
            ParamFactValue docNoParamFactValue = dataSet.get(0);
            docNo = docNoParamFactValue.getTheValue();
        }
        return docNo;
    }

    private boolean matchFilter(String itemName) {
        List<String> filterStr = Arrays.asList("设备编号","试验编号", "试样编号", "实验编号", "试验项目号","时间", "操作员", "委托单号");
        boolean match = filterStr.stream().anyMatch(o -> itemName.indexOf(o) >= 0);
        return match;
    }
}
