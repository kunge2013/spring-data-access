package com.convertools.handler;

import com.alibaba.fastjson.JSON;
import com.convertools.convertors.IConverter;
import com.convertools.dto.CusIntIOTSaveDTO;
import com.convertools.entity.CusIntIOTEntity;
import com.convertools.entity.MtsUploadRecord;
import com.convertools.repository.CusIntIOTRepository;
import com.convertools.repository.MtsUploadRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author fangkun
 * @date 2021/5/13 21:31
 * @description:
 */
@Service("sqlServerHandler")
public class SqlServerHandler implements UploadService {

    @Resource(name = "cusIntIOTSaveDTOConverter")
    private IConverter<String, List<CusIntIOTSaveDTO>> cusIntIOTSaveDTOConverter;

    /*设备编号*/
    @Value("${device.no:SHT4605/FM06008}")
    private String code;

    @Autowired
    private CusIntIOTRepository cusIntIOTRepository;

    @Autowired
    private MtsUploadRecordRepository mtsUploadRecordRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @Override
    public void execData(String filename) {
        batchSaveOrUpdateCusIntIOT(filename, cusIntIOTSaveDTOConverter.convert(filename));
    }

    private void batchSaveOrUpdateCusIntIOT(String fileName, List<CusIntIOTSaveDTO> dataSet) {
        if (CollectionUtils.isEmpty(dataSet)) {
            return;
        }
        for (CusIntIOTSaveDTO cusIntIOTSaveDTO : dataSet) {
            String docNo = cusIntIOTSaveDTO.getDocNo();
            String simpleNo = cusIntIOTSaveDTO.getSimpleNo();
            List<CusIntIOTEntity> list = cusIntIOTSaveDTO.getDataSet();
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }
            for (CusIntIOTEntity cusIntIOTEntity : list) {
                String eitem = cusIntIOTEntity.getEitem();
                CusIntIOTEntity oldCusIntIOTEntity = cusIntIOTRepository.findByEitemAndDocNoAndEcorder(eitem, docNo, code);
                if (oldCusIntIOTEntity != null) {
                    cusIntIOTEntity.setId(oldCusIntIOTEntity.getId());
                }
            }
            cusIntIOTRepository.saveAll(list);
            MtsUploadRecord mtsUploadRecord = mtsUploadRecordRepository.findByFileNameAndSampleNo(fileName, simpleNo);
            if (null == mtsUploadRecord) {
                mtsUploadRecord = new MtsUploadRecord();
                mtsUploadRecord.setDocNo(docNo);
                mtsUploadRecord.setFileName(fileName);
                mtsUploadRecord.setDocNo(docNo);
                mtsUploadRecord.setSampleNo(simpleNo);
                mtsUploadRecord.setUpdateTime(dateFormat.format(new Date()));
                mtsUploadRecord.setUploadStatus(1);
            }
            mtsUploadRecord.setUploadStatus(MtsUploadRecord.UPLOAD_SUCCESS);
            // 上传时间
            mtsUploadRecord.setUploadTime(dateFormat.format(new Date()));
            mtsUploadRecord.setData(JSON.toJSONString(cusIntIOTSaveDTO));
            mtsUploadRecordRepository.save(mtsUploadRecord);
        }
        cusIntIOTRepository.flush();
        mtsUploadRecordRepository.flush();
    }
}
