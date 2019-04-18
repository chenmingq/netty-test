package com.netty.test.annotation;


import java.lang.annotation.*;

/**
 * @author : chenmq
 * date : 2019-4-16
 * Project : netty-test
 * Description： 标记对象是否需要实现序列化
 */

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SerializableTag {
}
