package com.netty.test.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：jdbc工具
 */

public class JdbcUtils {

    public static void close (ResultSet resultSet, ConnectionPool connectionPool, Connection connection, PreparedStatement ps){
        try {
            if (null != resultSet) {
                resultSet.close();
            }
            if (null != ps) {
                ps.close();
            }
            if (null != connectionPool) {
                connectionPool.releaseConn(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
