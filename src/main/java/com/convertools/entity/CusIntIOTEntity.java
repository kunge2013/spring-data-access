package com.convertools.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fangkun
 * @date 2021/3/27 23:03
 * @description:
 */
@Entity(name = "Cus_Int_IOT")
@Setter
@Getter
public class CusIntIOTEntity implements Serializable {


    public static final CusIntIOTEntityFactory FACTORY = new CusIntIOTEntityFactory();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CreatedBy", columnDefinition = "CreatedBy")
    private String createdBy;

    @Column(name = "CreatedOn")
    private String createdOn;

    @Column(name = "TestBy")
    private String testBy;

    @Column(name = "TestOn")
    private String testOn;

    @Column(name = "Org")
    private String org;

    @Column(name = "DocNo")
    private String docNo;

    @Column(name = "Ecorder")
    private String ecoder;

    @Column(name = "SampleNo")
    private String sampleNo;


    @Column(name = "Esort")
    private String esort;

    @Column(name = "EItem")
    private String eitem;

    @Column(name = "Value")
    private String value;

    @Column(name = "EvaluationResult")
    private String evaluationResult;

    @Column(name = "A1")
    private String a1;

    @Column(name = "A2")
    private String a2;


    @Column(name = "A3")

    private String a3;
    @Column(name = "A4")
    private String a4;

    @Column(name = "A5")
    private String a5;

    @Column(name = "A6")
    private String a6;

    @Column(name = "A7")
    private String a7;

    @Column(name = "A8")
    private String a8;

    @Column(name = "A9")
    private String a9;

    @Column(name = "Status")
    private int status;

    @Column(name = "Mem")
    private String mem;

    @Column(name = "FileType")
    private String fileType = "MNSR";

    // 测试编号
    @Transient
    private String testNo;

    public static class CusIntIOTEntityFactory {
       public CusIntIOTEntity create(String createdBy, String testBy, String eitem, String value, String ecoder, String simpleNo, String esort, String evaluationResult, String createOn, String testOn, String docNo, String fileType) {
            CusIntIOTEntity cusIntIOTEntity = new CusIntIOTEntity();
            cusIntIOTEntity.setCreatedOn(createOn);
            cusIntIOTEntity.setTestOn(testOn);
            cusIntIOTEntity.setEitem(eitem);
            cusIntIOTEntity.setValue(value);
            cusIntIOTEntity.setCreatedBy(createdBy);
            cusIntIOTEntity.setTestBy(testBy);
            cusIntIOTEntity.setEcoder(ecoder);
            cusIntIOTEntity.setDocNo(docNo);
            cusIntIOTEntity.setSampleNo(simpleNo);
            cusIntIOTEntity.setEsort(esort);
            cusIntIOTEntity.setEvaluationResult(evaluationResult);
            /*DefaultVal*/
            cusIntIOTEntity.setStatus(0);
            cusIntIOTEntity.setOrg("3");
            //"MNSR"
            cusIntIOTEntity.setFileType(fileType);
           /*DefaultVal*/
            return cusIntIOTEntity;
        }
    }
}
