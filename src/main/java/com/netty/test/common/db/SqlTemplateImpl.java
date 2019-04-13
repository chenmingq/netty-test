package com.netty.test.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author : chenmq
 * date : 2019-4-13
 * Project : netty-test
 * Description： DB_crud
 */

public class SqlTemplateImpl {

    private ConnectionPool connectionPool;

    public SqlTemplateImpl() {
    }

    public SqlTemplateImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int insert(String sql, Object... params) {
        return 0;
    }


    public int update(String sql, Object... params) {
        return 0;
    }


    public int delete(String sql, Object... params) {
        return 0;
    }


    public <T> T query(Object... param) {
        if (null == this.connectionPool) {
            return null;
        }
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            conn = this.connectionPool.getConnection();
            if (null == conn) {
                return null;
            }
            preparedStatement = conn.prepareStatement("select * from p_role");
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != conn) {
                    this.connectionPool.releaseConn(conn);
                }
                if (null != preparedStatement) {
                    preparedStatement.close();
                }
                if (null != resultSet) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public <T> List<T> queryList(Object... param) {
        return null;
    }

    public static void main(String[] args) {

    }

}
