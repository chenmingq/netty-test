package com.netty.test.pojo.serializable;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.netty.test.serializer.factory.SerializableUtils;
import com.netty.test.pojo.proto.NettyTest;
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

        protoTest(deSerializerResult);
    }


    private static void protoTest (ListTest listTest){

        String jsonString = JSON.toJSONString(listTest);

        NettyTest.ResQueryTableData.Builder resQueryTableData = NettyTest.ResQueryTableData.newBuilder();
        resQueryTableData.setTableData(jsonString);
        NettyTest.ResQueryTableData build = resQueryTableData.build();

        byte[] bytes = build.toByteArray();

        System.out.println(build);
        System.out.println(Arrays.toString(bytes));

        System.out.println("----------------------------------");

        try {
            NettyTest.ResQueryTableData byteToProtoObj = NettyTest.ResQueryTableData.parseFrom(bytes);
            System.out.println(byteToProtoObj);
            System.out.println(jsonString);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
