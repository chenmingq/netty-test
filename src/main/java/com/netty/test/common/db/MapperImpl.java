package com.netty.test.common.db;

import com.netty.test.coder.serializer.SerializableFactory;
import com.netty.test.consts.ColumnTypeName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： mapper 操作对象
 */
public class MapperImpl implements MapperInter<Object> {

    private final static Logger LOG = LoggerFactory.getLogger(MapperImpl.class);


    @Override
    public Object mappingObj(ResultSet resultSet, int serializerType, Class<Object> clazz) {
        Map<String, Object> fieldMap = new HashMap<>();
        try {
            //结果集的元素对象
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //获取结果集的元素个数
            int colCount = rsmd.getColumnCount();
            //构造业务对象实体
            Object resultObj = clazz.newInstance();

            Field[] objFields = clazz.getDeclaredFields();
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
                            value = new SerializableFactory().deserializer(serializerType, (byte[]) value, clazz);
                            if (!value.getClass().equals(clazz)) {
                                break;
                            }
                            Field[] valueFields = value.getClass().getDeclaredFields();
                            for (Field valueField : valueFields) {
                                valueField.setAccessible(true);
                                Object valObj = valueField.get(value);
                                if (null == valObj) {
                                    continue;
                                }
                                String name = valueField.getName();
                                if (fieldMap.containsKey(name)) {
                                    continue;
                                }
                                fieldMap.put(name, valObj);
                            }
                            break;
                        default:
                            break;
                    }
                } else {
                    for (Field objField : objFields) {
                        String name = objField.getName();
                        if (!name.equalsIgnoreCase(columnName)) {
                            continue;
                        }
                        if (fieldMap.containsKey(name)) {
                            continue;
                        }
                        fieldMap.put(name, value);
                    }
                }
                LOG.info("columnName == {},columnType =={},columnTypeName == {}", columnName, columnType, columnTypeName);
            }

            if (fieldMap.isEmpty()) {
                return null;
            }
            for (Field objField : objFields) {
                String name = objField.getName();
                objField.setAccessible(true);
                Object objValue = fieldMap.get(objField.getName());
                if (null == objValue) {
                    continue;
                }
                String s = "set" + name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                Method method = clazz.getMethod(s, objField.getType());
                method.invoke(resultObj, objValue);
            }
            return resultObj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> mappingList(List<?> t) {
        return null;
    }
}
