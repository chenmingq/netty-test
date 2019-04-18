package com.netty.test.utils;


import com.netty.test.annotation.SerializableTag;
import com.netty.test.common.protostuff.ListTest;

/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description：object对象工具
 */

public class ObjectUtils {

    /**
     * 检查是否为jdk原生对象
     *
     * @param object
     * @return
     */
    public static boolean checkJdkObj(Object object) {
        if (null == object) {
            throw new RuntimeException("not this object");
        }
        boolean result = false;
        String name = object.getClass().getName();
        switch (name) {
            case "java.lang.Object":
            case "java.lang.String":
            case "java.lang.Integer":
            case "java.util.List":
            case "java.util.Map":
            case "java.util.Set":
                result = true;
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 是否需要序列化的对象
     *
     * @param obj
     * @return
     */
    public static boolean checkSerializableObj(Object obj) {
        if (null == obj) {
            throw new RuntimeException("not this object");
        }
        return obj.getClass().isAnnotationPresent(SerializableTag.class);
    }


    public static void main(String[] args) {
        System.out.println(checkSerializableObj(new ListTest()));
    }


}
