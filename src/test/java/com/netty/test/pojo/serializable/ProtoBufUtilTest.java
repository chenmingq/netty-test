package com.netty.test.pojo.serializable;

import com.netty.test.coder.serializer.SerializableUtils;
import com.netty.test.pojo.test.ListTest;
import com.netty.test.pojo.test.Student;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProtoBufUtilTest {

    public static void main(String[] args) {

        Student student = new Student();
        student.setName("lance");
        student.setAge(28);
        student.setStudentNo("2011070122");
        student.setSchoolName("BJUT");

        List<Student> list = new ArrayList<>();
        list.add(student);

        ListTest listTest = new ListTest();
        listTest.setStudents(list);

        byte[] serializerResult = SerializableUtils.serializer(listTest);
//        byte[] serializerResult = ProtoStuffUtils.serializer(listTest);

        System.out.println("serializer result:" + Arrays.toString(serializerResult));

//        ListTest deSerializerResult = ProtoStuffUtils.deserializer(serializerResult,ListTest.class);
        ListTest deSerializerResult = (ListTest) SerializableUtils.deserializer(serializerResult);

        System.out.println("deSerializerResult:" + deSerializerResult.toString());
    }
}
