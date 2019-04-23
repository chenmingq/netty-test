package com.netty.test.common.db;

import com.netty.test.pojo.db.DbParam;

import java.sql.Connection;

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
     */
    Connection getConnection();

    /**
     * 关闭连接
     * @param connection
     */
    void releaseConn(Connection connection);

    /**
     * 数据库连接配置
     * @param dbParam
     * @param dev
     */
    void loginDb(DbParam dbParam, boolean dev);
}
