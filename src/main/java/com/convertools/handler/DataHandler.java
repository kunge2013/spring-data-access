package com.convertools.handler;

import com.convertools.entity.ParamFactValue;
import com.convertools.entity.UpData;

import java.util.List;
import java.util.Map;

/**
 * @author fangkun
 * @date 2020/11/12 10:02
 * @description:
 */
public interface DataHandler {
    void handler();

    /**
     *
     * @param savefilename
     * @return
     */
    public Map<Integer, List<ParamFactValue>> transtDataByfileName(String savefilename);

    /**
     *
     * @param fileName
     * @param paramFactValues
     * @return
     */
    public UpData convertByParamFactValues(String fileName, List<ParamFactValue> paramFactValues);

    /**
     * 单机版执行
     * @param filename
     */
    public void callHttp(String filename);
}