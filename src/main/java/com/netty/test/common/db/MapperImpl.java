package com.netty.test.common.db;

import com.netty.test.common.protostuff.ListTest;
import com.netty.test.common.protostuff.ProtoBufUtil;
import com.netty.test.common.protostuff.Student;
import com.netty.test.consts.ColumnTypeName;
import io.protostuff.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

public class MapperImpl implements MapperInter<ResultSet, Class> {

    private final static Logger LOG = LoggerFactory.getLogger(MapperImpl.class);

    @Override
    public <RS> RS mappingObj(ResultSet resultSet, Class clazz) {
        try {
            //结果集的元素对象
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //获取结果集的元素个数
            int colCount = rsmd.getColumnCount();
            //业务对象的属性数组
            Field[] fields = clazz.getDeclaredFields();
            //构造业务对象实体
            Object obj = clazz.newInstance();
            //将每一个字段取出进行赋值
            for (int i = 1; i <= colCount; i++) {
                //获取列名
                String columnName = rsmd.getColumnName(i);
                int columnType = rsmd.getColumnType(i);
                String columnTypeName = rsmd.getColumnTypeName(i);

                Object value = resultSet.getObject(i);

                ColumnTypeName.MYSQL_COLUMN_TYPE_NAME COLUMN_TYPE_NAME = ColumnTypeName.MYSQL_COLUMN_TYPE_NAME.parse(columnTypeName);
                if (null != COLUMN_TYPE_NAME) {
                    switch (COLUMN_TYPE_NAME) {
                        case TinyBlob:
                        case Blob:
                        case MediumBlob:
                        case LongBlob:
                            // listTest
                            value = ProtoBufUtil.deserializer((byte[]) value, clazz);

                            if (!value.getClass().equals(clazz) ) {
                                break;
                            }
                            Field[] declaredFields = clazz.getDeclaredFields();

                            Field[] objFields = obj.getClass().getDeclaredFields();
                            for (Field declaredField : declaredFields) {
                                boolean annotationPresent = declaredField.isAnnotationPresent(Tag.class);
                                if (!annotationPresent) {
                                    continue;
                                }
                                for (Field objField : objFields) {
                                    if (objField.getClass() != declaredField.getClass()) {
                                        continue;
                                    }
                                    boolean flag = objField.isAccessible();
                                    objField.setAccessible(true);
                                    objField.set(obj, value);
                                    objField.setAccessible(flag);
                                    break;
                                }

                                System.out.println(obj);
//                                Class<?> type = declaredField.getType();
//                                if ("List".equals(type.getSimpleName())) {
//                                    System.out.println(value);
//                                }
                            }
                            break;
                        default:
                            break;
                    }
                }
                LOG.info("columnName == {},columnType =={},columnTypeName == {}", columnName, columnType, columnTypeName);
                //寻找该列对应的对象属性
                for (int j = 0; j < fields.length; j++) {
                    Field f = fields[j];
                    //如果匹配进行赋值
                    if (f.getName().equalsIgnoreCase(columnName)) {
                        boolean flag = f.isAccessible();
                        f.setAccessible(true);
                        f.set(obj, value);
                        f.setAccessible(flag);
                    }
                }
            }
            return (RS) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <RS> List<RS> mappingList(List<ResultSet> t) {
        return null;
    }
}
