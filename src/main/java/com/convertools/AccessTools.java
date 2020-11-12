package com.convertools;

import java.sql.*;

/**
 * @author fangkun
 * @date 2020/11/4 15:25
 * @description:
 */
public class AccessTools {

    /**
     * Access数据库Connection
     */
    private Connection connection;

    static {
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");//加载ucanaccess驱动
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Java 获取 Access 数据库连接(Connection)
     * <table border="1" cellpadding="8">
     * <tr><td colspan="2" align="center">Java 获取  Access 数据库连接(Connection) 注释 </td></tr>
     * <tr><th>输入参数</th><th>参数解释</th></tr>
     * <tr><td align="center">path</td><td>Access文件的相对或者绝对路径(支持*.mdb和*.accdb数据库文件)</td></tr>
     * <tr><td align="center">user</td><td>用户账号(如果没有就写"")</td></tr>
     * <tr><td align="center">pwd</td><td>密码密码(如果没有就写"")</td></tr>
     * </table>
     * @param path Access文件的相对或者绝对路径(支持*.mdb和*.accdb数据库文件)
     * @param user 用户账号(如果没有就写"")
     * @param pwd  密码密码(如果没有就写"")
     * @return: Access 的 Connection
     * @date: 2019年1月24日 12:47:12
     */
    public Connection getAccessConnection(String path, String user, String pwd) {
        try {
            //获取Access数据库连接(Connection)
            this.connection = DriverManager.getConnection("jdbc:ucanaccess://" + path, user, pwd);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return this.connection;
    }

    public static void main(String[] args) throws Exception {
        AccessTools access=new AccessTools();
        Connection connection = access.getAccessConnection("D:\\Program Files (x86)\\PowerTestV3.5-SHT\\data\\1--jk-ye2011(12)00012-11.mdb", "", "");
        access.select(connection);
    }
    /**
     * Access查询
     *
     * @param connection 连接
     * @throws Exception 异常
     * @date: 2019年1月24日 12:47:12
     */
    public void select(Connection connection) throws Exception {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("select * from student");
        while (result.next()) {
            System.out.print(result.getString("id") + "\t");
            System.out.print(result.getString("name") + "\t");
            System.out.print(result.getString("address") + "\t");
            System.out.print(result.getString("age") + "\t");
            System.out.print(result.getString("birthday") + "\t");
            System.out.println();
        }
        statement.close();
        connection.close();
    }
}
