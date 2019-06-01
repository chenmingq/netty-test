package com.netty.test.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.netty.test.utils.LordPropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Properties;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：数据源配置
 * <p>
 * 连接池是一个对数据库连接进行管理的东西，
 * 当一个线程需要用 JDBC 对 数据库操作时，它从池中请求一个连接。
 * 当这个线程使用完了这个连接，将它返回到连接池中，
 * 这样这就可以被其它想使用它的线程使用，而不是每次都重新建立一个数据库连接。
 */

public class DbConnectionPool {

    private final static Logger LOG = LoggerFactory.getLogger(DbConnectionPool.class);

    private static DbConnectionPool dbPoolConnection = null;
    private static DruidDataSource druidDataSource = null;

    private static final String DB_SERVER_PROPERTIES = "db_server.properties";

    private static DbConnectionPool instance = new DbConnectionPool();

    public static DbConnectionPool getDbPoolConnection() {
        return dbPoolConnection;
    }

    public DbConnectionPool() {
    }

    /**
     * 返回druid数据库连接
     *
     * @return dbConnection
     * @throws SQLException
     */
    public DruidPooledConnection getConnection() throws SQLException {
        return druidDataSource.getConnection();
    }

    /**
     * 关闭资源
     *
     * @return
     */
    public boolean close() {
        if (null == druidDataSource) {
            return false;
        }
        druidDataSource.close();
        return true;
    }

    static {
        Properties properties = LordPropertiesUtils.lordProperties(DB_SERVER_PROPERTIES);
        if (null == properties) {
            throw new RuntimeException("the source is not");
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
    }

}
