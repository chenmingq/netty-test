package com.netty.test.common.protostuff;

import io.protostuff.Exclude;
import io.protostuff.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ListTest {

    @Tag(1)
    private int id;

    @Tag(2)
    private List<Student> students = new ArrayList<>();

    @Exclude
    private String name;

}
