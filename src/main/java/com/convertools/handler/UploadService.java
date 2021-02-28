package com.convertools.handler;

import com.convertools.entity.CheckResult;
import com.convertools.entity.ParamFactValue;
import com.convertools.entity.UpData;

import java.util.List;
import java.util.Map;

/**
 * @author fangkun
 * @date 2020/12/12 16:45
 * @description:
 */
public interface UploadService {

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
    public UpData convertByParamFactValues(int simpleNo, String fileName, List<ParamFactValue> paramFactValues);

    /**
     * 单机版执行
     * @param filename
     */
    public void callHttp(String filename);

    /**
     * 支持扩展
     * @param filename
     */
    public void callHttpExt(String filename);
    //JOptionPane.showMessageDialog(null, "上传失败!", "", JOptionPane.ERROR_MESSAGE);

    public CheckResult checkUpData(UpData upData);

    public CheckResult checkUpDataMap(Map<String, Object> upData);


}
