package com.convertools.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author fangkun
 * @date 2020/12/10 20:13
 * @description:
 */
public class UpData {

//    "code":"美特斯",
    private String code= "美特斯拉伸机";
//            "ECorder":"1",

    @JSONField(name = "ECorder")
    private String ECorder;
//            "SampleNo":"2",

    @JSONField(name = "SampleNo")
    private String SampleNo;
//            "operators":"3",
@JSONField(name = "operators")
    private String operators;
//            "WorkTime":"4",

    @JSONField(name = "WorkTime")
    private String WorkTime;
//            "do":6,
    @JSONField(name = "do")
    private float doVal;
//            "du":6,
@JSONField(name = "du")
    private double du;
//            "lo":6,

    @JSONField(name = "lo")
    private double lo;
    //            "lu":6,
    private double lu;
//            "Fm":6,

    @JSONField(name = "Fm")
    private double Fm;
    //            "A":8,
    @JSONField(name = "A")
    private double A;
    //            "Z":8,
    @JSONField(name = "Z")
    private double Z;
    //            "Rm":6,

    @JSONField(name = "Rm")
        private double Rm;
    //            "Fp":6,
    @JSONField(name = "Fp")
    private double Fp;
    //            "ReH":6,
    @JSONField(name = "ReH")
        private double ReH;
    //            "FeH":6,
    @JSONField(name = "FeH")
    private double FeH;
    //            "ReL":6,
    @JSONField(name = "ReL")
    private double ReL;
    //            "FeL":6,

    @JSONField(name = "FeL")
    private double FeL;
    //            "Rt":6,
    @JSONField(name = "Rt")
    private double Rt;
    //            "Ft":6,

    @JSONField(name = "Ft")
    private double Ft;
    //            "RP":6
    @JSONField(name = "RP")
    private double RP;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getECorder() {
        return ECorder;
    }

    public void setECorder(String ECorder) {
        this.ECorder = ECorder;
    }

    public String getSampleNo() {
        return SampleNo;
    }

    public void setSampleNo(String sampleNo) {
        SampleNo = sampleNo;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getWorkTime() {
        return WorkTime;
    }

    public void setWorkTime(String workTime) {
        WorkTime = workTime;
    }

    public float getDoVal() {
        return doVal;
    }

    public void setDoVal(float doVal) {
        this.doVal = doVal;
    }

    public double getDu() {
        return du;
    }

    public void setDu(double du) {
        this.du = du;
    }

    public double getLo() {
        return lo;
    }

    public void setLo(double lo) {
        this.lo = lo;
    }

    public double getLu() {
        return lu;
    }

    public void setLu(double lu) {
        this.lu = lu;
    }

    public double getFm() {
        return Fm;
    }

    public void setFm(double fm) {
        Fm = fm;
    }

    public double getA() {
        return A;
    }

    public void setA(double a) {
        A = a;
    }

    public double getZ() {
        return Z;
    }

    public void setZ(double z) {
        Z = z;
    }

    public double getRm() {
        return Rm;
    }

    public void setRm(double rm) {
        Rm = rm;
    }

    public double getFp() {
        return Fp;
    }

    public void setFp(double fp) {
        Fp = fp;
    }

    public double getReH() {
        return ReH;
    }

    public void setReH(double reH) {
        ReH = reH;
    }

    public double getFeH() {
        return FeH;
    }

    public void setFeH(double feH) {
        FeH = feH;
    }

    public double getReL() {
        return ReL;
    }

    public void setReL(double reL) {
        ReL = reL;
    }

    public double getFeL() {
        return FeL;
    }

    public void setFeL(double feL) {
        FeL = feL;
    }

    public double getRt() {
        return Rt;
    }

    public void setRt(double rt) {
        Rt = rt;
    }

    public double getFt() {
        return Ft;
    }

    public void setFt(double ft) {
        Ft = ft;
    }

    public double getRP() {
        return RP;
    }

    public void setRP(double RP) {
        this.RP = RP;
    }

    public UpData() {
    }
}
