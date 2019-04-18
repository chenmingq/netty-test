package com.netty.test.common.db;

import com.netty.test.coder.serializer.SerializableFactory;
import com.netty.test.common.protostuff.ListTest;
import com.netty.test.common.protostuff.Student;
import com.netty.test.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author : chenmq
 * date : 2019-4-13
 * Project : netty-test
 * Description： jdbc模板
 */

public class JdbcTemplateImpl {

    private final static Logger LOG = LoggerFactory.getLogger(JdbcTemplateImpl.class);

    private ConnectionPool connectionPool;

    public JdbcTemplateImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * 添加数据
     *
     * @param sql
     * @param serializerType
     * @param params
     * @return
     */
    public int insert(String sql, int serializerType, Object[] params) {
        if (null == params) {
            return 0;
        }
        Connection conn;
        PreparedStatement ps = null;
        int result = 0;
        conn = connectionPool.getConnection();
        if (null == conn) {
            return 0;
        }
        try {
            conn.setAutoCommit(false);
            List<Object> sqlParamsList = new LinkedList<>();
            for (Object param : params) {
                boolean checkSerializableObj = ObjectUtils.checkSerializableObj(param);
                if (!checkSerializableObj) {
                    sqlParamsList.add(param);
                } else {
                    byte[] serializerResult = new SerializableFactory().serializer(serializerType, param);
                    sqlParamsList.add(serializerResult);
                }
            }
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < sqlParamsList.size(); i++) {
                ps.setObject(i + 1, sqlParamsList.get(i));
            }
            result += ps.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JdbcUtils.close(null, connectionPool, conn, ps);
        }
        return result;
    }

    /**
     * 查询单个对象
     *
     * @param sql
     * @param clazz
     * @param serializerType
     * @param params
     * @param <T>
     * @return
     */
    public <T> T query(String sql, Class<?> clazz, int serializerType, Object... params) {
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
                Object o = mapperInter.mappingObj(resultSet, serializerType, clazz);
                return (T) o;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(resultSet, connectionPool, conn, ps);
        }

        return null;
    }


    /**
     * 查询列表
     *
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public <T> List<T> queryList(String sql, Object... params) {
        return null;
    }

    /**
     * 查询库
     *
     * @param dbName
     * @return
     */
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
            String sql = String.format("Select * From Information_Schema.Tables Where Table_Schema = '%s' ", dbName);

            System.out.println(sql);
            ps = conn.prepareStatement(sql);
            resultSet = ps.executeQuery();
            List<String> tableNameList = new ArrayList<>();
            while (resultSet.next()) {
                tableNameList.add(resultSet.getString(1));
            }
            return tableNameList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(resultSet, connectionPool, conn, ps);
        }
        return null;
    }


    public static void main(String[] args) {
        DbConnectionPool instance = DbConnectionPool.getInstance();
        List<Student> list = new ArrayList<>();

        Map<Integer, Student> map = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            Student student = new Student();
            student.setName("lance");
            student.setAge(i);
            student.setStudentNo(String.valueOf(new Random().nextInt(100)));
            student.setSchoolName("BJUT");
            list.add(student);
            map.put(i, student);
        }
        ListTest listTest = new ListTest();
        listTest.setStudents(list);
        listTest.setMap(map);
        Object[] objects = new Object[2];
        objects[0] = listTest;
        objects[1] = "zhangsan";
        new JdbcTemplateImpl(instance).insert("insert into test(datas,user) values (?,?)", 1, objects);
        ListTest query = new JdbcTemplateImpl(instance).query("select * from test where id = ?", ListTest.class, 1, 212);
        System.out.println(query);
        List<String> stringList = new JdbcTemplateImpl(instance).queryTables("chenmq");
        System.out.println(stringList);
        System.exit(0);
    }

}
