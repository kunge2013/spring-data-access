package com.convertools.dto;

import com.convertools.entity.CusIntIOTEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangkun
 * @date 2021/5/13 13:07
 * @description:
 */
public class CusIntIOTSaveDTO {
    // 委托单号
    private String docNo;

    private String simpleNo;

    // 需要保存的分组数据
    private List<CusIntIOTEntity> dataSet = new ArrayList<>();

    public String getDocNo() {
        return docNo;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public List<CusIntIOTEntity> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<CusIntIOTEntity> dataSet) {
        this.dataSet = dataSet;
    }

    public void addCusIntIOTEntity(CusIntIOTEntity cusIntIOTEntity) {
        this.dataSet.add(cusIntIOTEntity);
    }

    public String getSimpleNo() {
        return simpleNo;
    }

    public void setSimpleNo(String simpleNo) {
        this.simpleNo = simpleNo;
    }
}
