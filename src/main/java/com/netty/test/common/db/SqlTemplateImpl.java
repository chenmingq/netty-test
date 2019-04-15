package com.netty.test.common.db;

import com.netty.test.common.protostuff.ListTest;
import com.netty.test.common.protostuff.ProtoBufUtil;
import com.netty.test.common.protostuff.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : chenmq
 * date : 2019-4-13
 * Project : netty-test
 * Description： DB_crud
 */

public class SqlTemplateImpl {

    private final static Logger LOG = LoggerFactory.getLogger(SqlTemplateImpl.class);

    private ConnectionPool connectionPool;

    public SqlTemplateImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int insert(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        int result = 0;
        try {
            conn = connectionPool.getConnection();
            if (null == conn) {
                return 0;
            }
            if (null != params) {
                for (Object param : params) {
                    byte[] serializerResult = ProtoBufUtil.serializer(param);
                    ps = conn.prepareStatement(sql);
                    ps.setBytes(1, serializerResult);
                    result += ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != conn) {
                    this.connectionPool.releaseConn(conn);
                }
                if (null != ps) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public <T> T query(String sql, T t, Object... params) {
        if (null == this.connectionPool) {
            return null;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = this.connectionPool.getConnection();
            ps = conn.prepareStatement(sql);
            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            resultSet = ps.executeQuery();
            MapperInter mapperInter = new MapperImpl();
            while (resultSet.next()) {
                return (T) mapperInter.mappingObj(resultSet, t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != resultSet) {
                    resultSet.close();
                }
                if (null != ps) {
                    ps.close();
                }
                if (null != conn) {
                    this.connectionPool.releaseConn(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    public <T> List<T> queryList(String sql, Object... params) {
        return null;
    }

    public List<String> queryTables(String dbName) {
        //Select Table_Name From Information_Schema.Tables Where Table_Schema = 'mysql'
        if (null == this.connectionPool) {
            return null;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            conn = this.connectionPool.getConnection();
            ps = conn.prepareStatement("Select Table_Name From Information_Schema.Tables Where Table_Schema = '" + dbName + "'");
            resultSet = ps.executeQuery();
            List<String> tableNameList = new ArrayList<>();
            while (resultSet.next()) {
                tableNameList.add(resultSet.getString(1));
            }
            return tableNameList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != resultSet) {
                    resultSet.close();
                }
                if (null != ps) {
                    ps.close();
                }
                if (null != conn) {
                    this.connectionPool.releaseConn(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) {
        DbConnectionPool instance = DbConnectionPool.getInstance();
        List<Student> list = new ArrayList<>();
        for (int a = 0; a < 10; a++) {
            for (int i = 0; i < 20; i++) {
                Student student = new Student();
                student.setName("lance");
                student.setAge(28);
                student.setStudentNo("2011070122");
                student.setSchoolName("BJUT");
                list.add(student);
            }
        }
        ListTest listTest = new ListTest();
        listTest.setStudents(list);
        new SqlTemplateImpl(instance).insert("insert into test(datas) values (?)", listTest);
        Object query = new SqlTemplateImpl(instance).query("select * from test where id = ?", ListTest.class, 25);

        System.out.println(query);
        System.exit(0);
    }

}
