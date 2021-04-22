package com.convertools.handler;

import com.convertools.entity.ParamFactValue;
import com.convertools.entity.UpData;

import java.util.List;
import java.util.Map;

/**
 * @author fangkun
 * @date 2020/12/12 16:45
 * @description: 上传
 */
public interface UploadService {


    /**
     * 支持扩展
     * @param filename
     */
    public void callHttpExt(String filename);
}
