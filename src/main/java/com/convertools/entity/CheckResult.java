package com.convertools.entity;

/**
 * @author fangkun
 * @date 2021/2/28 7:54
 * @description:
 */
public enum CheckResult {

    SUCCESS(0, "校验成功!"),
    SIMPLENO_EMPTY(1, "式样编号为空!"),
    ECORDER_EMPTY(2, "委托单号为空!");

    public int code;

    public String desc;

    CheckResult(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
