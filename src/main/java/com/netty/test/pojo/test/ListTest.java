package com.netty.test.pojo.test;

import com.netty.test.annotation.SerializableTag;
import io.protostuff.Exclude;
import io.protostuff.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SerializableTag
@Data
public class ListTest implements Serializable {
    private static final long serialVersionUID = 0L;
    @Tag(1)
    private int id;

    @Tag(2)
    private List<Student> students = new ArrayList<>();

    @Tag(3)
    private Map<Integer,Student> map = new HashMap<>();

    @Exclude
    private transient String name;

}
