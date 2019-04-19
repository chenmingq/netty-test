package com.netty.test.annotation;


import java.lang.annotation.*;

/**
 * @author : chenmq
 * date : 2019-4-20
 * Project : netty-test
 * Description： 参数名称
 */

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ParamName {

    /**
     * 形参名称
     *
     * @return
     */
    String name();
}
