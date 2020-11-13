package com.convertools.entity;

/**
 * @author fangkun
 * @date 2020/11/13 13:04
 * @description: 参数对象
 */
public class ParamFactValue extends BaseEntity {

    /*试验编号*/
    private int TestNo;


    /*参数项的先后顺序，若是纯的结果参数，加1000作偏移量*/
    private int littleNo;


    /*若是扩展公式，那么从扩展公式代号、图标或名称中来；若不是扩展公式，则是自定义的名称*/
    private String Name;


    /*选中参数的具体值*/
    private String TheValue;


    /*单位*/
    private String Unit;


    /*0仅为用户参数，1仅为结果参数，2既是用户又是结果参数*/
    private int UserOrResultParam;

    public int getTestNo() {
        return TestNo;
    }

    public void setTestNo(int testNo) {
        TestNo = testNo;
    }

    public int getLittleNo() {
        return littleNo;
    }

    public void setLittleNo(int littleNo) {
        this.littleNo = littleNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTheValue() {
        return TheValue;
    }

    public void setTheValue(String theValue) {
        TheValue = theValue;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public int getUserOrResultParam() {
        return UserOrResultParam;
    }

    public void setUserOrResultParam(int userOrResultParam) {
        UserOrResultParam = userOrResultParam;
    }

    public ParamFactValue(int testNo, int littleNo, String name, String theValue, String unit, int userOrResultParam) {
        TestNo = testNo;
        this.littleNo = littleNo;
        Name = name;
        TheValue = theValue;
        Unit = unit;
        UserOrResultParam = userOrResultParam;
    }

    public ParamFactValue() {
    }
}
