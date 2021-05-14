package com.convertools.handler.service.impl;

import com.convertools.handler.service.IUpdateMdbDocNo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * @author fangkun
 * @date 2021/5/14 12:46
 * @description:
 */
@Service
@Slf4j
public class UpdataMdbDocNo implements IUpdateMdbDocNo {
    /**
     * 会写委托单号
     * @param connection 连接
     * @param testNo
     * @param docNo
     */
    @Override
    public void updateDocNo(Connection connection, Integer testNo, String docNo) {
        String name = "委托单号";
        PreparedStatement updateStatement = null;
        PreparedStatement insertStatement = null;
        Statement queryStatement = null;
        int littleNo = 0;
        String TheValue = docNo;
        String Unit = "";
        String UserOrResultParam = "0";
        boolean execute = false;
        try {

            String querySql = "select TestNo,littleNo,Name,TheValue,Unit,UserOrResultParam from ParamFactValue where TestNo = " + testNo +" and Name = '" + name + "'";
            String updateSql = "update ParamFactValue set TheValue = ? where TestNo = ? and Name = ?";
            String insertSql = "insert into ParamFactValue (TestNo,littleNo,Name,TheValue,Unit,UserOrResultParam) values(?,?,?,?,?,?)";
            queryStatement =connection.createStatement();
            ResultSet resultSet = queryStatement.executeQuery(querySql);
            if (resultSet.next()) {
                updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, docNo);
                updateStatement.setInt(2, testNo);
                updateStatement.setString(3, name);
                execute = updateStatement.execute();
            } else {
                // TestNo,littleNo,Name,TheValue,Unit,UserOrResultParam
                insertStatement = connection.prepareStatement(insertSql);
                insertStatement.setInt(1, testNo);
                insertStatement.setInt(2, littleNo);
                insertStatement.setString(3, name);
                insertStatement.setString(4, docNo);
                insertStatement.setString(5, Unit);
                insertStatement.setString(6, UserOrResultParam);
                execute = insertStatement.execute();
            }
            log.info("add success !!!", execute);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (queryStatement != null) {
                try {
                    queryStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (insertStatement != null) {
                try {
                    insertStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (updateStatement != null) {
                try {
                    updateStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }

    }
}
