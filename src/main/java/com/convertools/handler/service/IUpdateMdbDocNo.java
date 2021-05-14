package com.convertools.handler.service;

import java.sql.Connection;

/**
 * @author fangkun
 * @date 2021/5/14 12:44
 * @description:
 */
public interface IUpdateMdbDocNo {
    void updateDocNo(Connection connection, Integer testNo, String docNo);
}
