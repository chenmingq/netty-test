package com.netty.test.db;

import com.netty.test.common.db.ConnectionPool;
import com.netty.test.common.db.DbConnectionPool;
import com.netty.test.common.db.JdbcTemplateImpl;
import com.netty.test.proto.serializer.SerializeType;
import com.netty.test.pojo.db.DbParam;
import com.netty.test.pojo.db.InformationSchemaTables;
import com.netty.test.pojo.test.ListTest;
import com.netty.test.pojo.test.Student;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JdbcTemplateImplTest {

    private final static Logger LOG = LoggerFactory.getLogger(JdbcTemplateImplTest.class);

    public static void main(String[] args) {
        DbConnectionPool instance = DbConnectionPool.getInstance();
        DbParam dbParam = new DbParam("localhost",3306,"chenmq","root","123456","","mysql","utf8");
        instance.loginDb(dbParam,true);
        insert(instance);
        /*insert(instance);
        queryObj(instance);
        querySchema(instance);
        queryList(instance);*/
        querySchema(instance);
        LOG.info("{}",instance);
    }

    private static void insert(ConnectionPool pool) {
        for (int a = 0; a < 10; a++) {
            List<Student> list = new ArrayList<>();

            Map<Integer, Student> map = new HashMap<>();
            for (int i = 0; i < 20; i++) {
                Student student = new Student();
                student.setName(RandomStringUtils.randomAlphabetic(10));
                student.setAge(i);
                student.setStudentNo(String.valueOf(new Random().nextInt(100)));
                student.setSchoolName(RandomStringUtils.randomAlphabetic(6));
                list.add(student);
                map.put(i, student);
            }
            ListTest listTest = new ListTest();
            listTest.setStudents(list);
            listTest.setMap(map);
            Object[] objects = new Object[2];
            objects[0] = listTest;
            objects[1] = "zhangsan";
            new JdbcTemplateImpl(pool).insert("insert into test(datas,user) values (?,?)", SerializeType.JDK_SERIALIZABLE.getType(), objects);
        }
    }

    private static void queryObj(ConnectionPool pool) {
        Object query = new JdbcTemplateImpl(pool).query("select * from test where id = ?", Object.class, SerializeType.JDK_SERIALIZABLE.getType(), 391);
        System.out.println(query);
    }

    private static void querySchema(ConnectionPool pool) {
        String sql = String.format("Select * From Information_Schema.Tables Where Table_Schema = '%s' ", "chenmq");
        List<InformationSchemaTables> informationSchemaTables = new JdbcTemplateImpl(pool).queryList(sql, InformationSchemaTables.class, SerializeType.JDK_SERIALIZABLE.getType());
        informationSchemaTables.forEach(System.out::println);
    }

    private static void queryList(ConnectionPool pool) {
        String sql = String.format("select * from test where id >= %s", 391);
        List<ListTest> listTests = new JdbcTemplateImpl(pool).queryList(sql, ListTest.class, SerializeType.JDK_SERIALIZABLE.getType());
        System.out.println(listTests.size());
        listTests.forEach(i -> System.out.println(i + "\r\n"));
    }
}
