package com.netty.test.common.db;

import com.netty.test.common.protostuff.ProtoBufUtil;
import com.netty.test.common.protostuff.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Wrapper;
/**
 * @author : chenmq
 * date : 2019-4-13
 * Project : netty-test
 * Description： DB_crud
 */

public class SqlTemplateImpl {

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

    public <T> T query(String sql,Object... params) {
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
            while (resultSet.next()) {
                return (T) resultSet.getObject("datas");
            }
        } catch (SQLException e) {
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

    public static void main(String[] args) {

        DbConnectionPool instance = DbConnectionPool.getInstance();
        /*List<Student> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.setName("lance");
            student.setAge(28);
            student.setStudentNo("2011070122");
            student.setSchoolName("BJUT");
            list.add(student);
        }

        new SqlTemplateImpl(instance).insert("insert into test(datas) values (?)", list);*/
        Object query = new SqlTemplateImpl(instance).query("select * from test where id = ?", 5);
        Student deserializer = ProtoBufUtil.deserializer((byte[]) query, Student.class);
        System.out.println(deserializer);
        System.exit(0);
    }


    private static class WrapperImpl<TT> implements Wrapper {

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }
    }

}
