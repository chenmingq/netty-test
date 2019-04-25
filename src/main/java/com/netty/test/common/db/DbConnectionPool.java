package com.netty.test.common.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.netty.test.pojo.db.DbParam;
import com.netty.test.proto.CommonConst;
import com.netty.test.utils.LordPropertiesUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：druid连接池
 * <p>
 * 连接池是一个对数据库连接进行管理的东西，
 * 当一个线程需要用 JDBC 对 数据库操作时，它从池中请求一个连接。
 * 当这个线程使用完了这个连接，将它返回到连接池中，
 * 这样这就可以被其它想使用它的线程使用，而不是每次都重新建立一个数据库连接。
 */

public class DbConnectionPool implements ConnectionPool {

    private final static Logger LOG = LoggerFactory.getLogger(DbConnectionPool.class);

    private DruidDataSource druidDataSource = null;

    private static DbConnectionPool instance = new DbConnectionPool();

    public static DbConnectionPool getInstance() {
        return instance;
    }

    public DbConnectionPool() {
    }

    /**
     * 返回druid数据库连接
     *
     * @return dbConnection
     */
    @Override
    public Connection getConnection() {
        DruidPooledConnection connection = null;
        try {
            connection = druidDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭资源
     */
    @Override
    public void releaseConn(Connection connection) {
        try {
            if (null == connection) {
                return;
            }
            connection.close();
        } catch (SQLException e) {
            LOG.error("{}", e.getMessage());
        }
    }

    @Override
    public void loginDb(DbParam dbParam, boolean dev) {

        Properties properties = LordPropertiesUtils.lordProperties(CommonConst.DB_SERVER_PROPERTIES);
        if (null == properties) {
            throw new RuntimeException("the source is not");
        }
        if (!dev) {
            boolean b = dbParam.checkFieldsIsEmpty();
            if (!b) {
                throw new RuntimeException("参数不正确");
            }

            properties.setProperty("username", dbParam.getUserName());
            properties.setProperty("password", dbParam.getPassword());
            if (!StringUtils.isEmpty(dbParam.getUrl())) {
                properties.setProperty("url", dbParam.getUrl());
            } else {
                properties.setProperty("dbName", dbParam.getDbName());
                properties.setProperty("host", dbParam.getHost());
                properties.setProperty("port", Integer.toString(dbParam.getPort()));
                String linkUrl = "jdbc:mysql://" + dbParam.getHost() +
                        ":" +
                        dbParam.getPort() +
                        "/" +
                        dbParam.getDbName() +
                        "?characterEncoding=" +
                        dbParam.getCharacterEncoding() +
                        "&serverTimezone=" +
                        "GMT%2B8";
                properties.setProperty("url", linkUrl);
            }
        }

        try {
            druidDataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            DruidPooledConnection connection = druidDataSource.getConnection();
            LOG.info("{}", connection);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        DbConnectionPool instance = DbConnectionPool.getInstance();
        DbParam dbParam = new DbParam("localhost",3306,"chenmq","root","123456","","mysql","utf8");
        instance.loginDb(dbParam,false);
        Connection connection = instance.getConnection();
        System.out.println(connection);
    }

}
