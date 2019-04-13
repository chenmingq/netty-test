package com.netty.test.common.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author : chenmq
 * date : 2019-4-13
 * Project : netty-test
 * Description： db会话连接池
 */
public interface ConnectionPool {

    /**
     *  与特定数据库的连接（会话）。
     * @return
     * @throws SQLException
     */
    Connection getConnection() throws SQLException;

    /**
     * 关闭连接
     * @param connection
     * @throws SQLException
     */
    void releaseConn(Connection connection) throws SQLException;
}
