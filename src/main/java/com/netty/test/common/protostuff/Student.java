package com.netty.test.common.protostuff;

import io.protostuff.Tag;
import lombok.Data;

import java.io.Serializable;

/**
 *  // 关于@Tag,要么所有属性都有@Tag注解,要么都没有,不能一个类中只有部分属性有@Tag注解
 */
@Data
public class Student implements Serializable {

    @Tag(1)
    private String name;
    @Tag(2)
    private String studentNo;
    @Tag(3)
    private int age;
    @Tag(4)
    private String schoolName;


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", studentNo='" + studentNo + '\'' +
                ", age=" + age +
                ", schoolName='" + schoolName + '\'' +
                '}';
    }
}
