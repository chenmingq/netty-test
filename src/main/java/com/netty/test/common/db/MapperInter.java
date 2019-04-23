package com.netty.test.common.db;

import java.sql.ResultSet;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： mapper 操作对象
 */
public interface MapperInter<T> {


    /**
     * 处理单个对象
     *
     * @param resultSet
     * @param serializerType
     * @param clazz
     * @return
     */
    T mappingObj(ResultSet resultSet, byte serializerType, Class<T> clazz);


}
