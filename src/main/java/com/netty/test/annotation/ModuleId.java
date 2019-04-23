package com.netty.test.annotation;

import java.lang.annotation.*;

/**
 * @author : chenmq
 * date : 2019-4-18
 * Project : netty-test
 * Description： 消息模块id
 */

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ModuleId {

    int id();

}
