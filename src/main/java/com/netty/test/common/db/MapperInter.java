package com.netty.test.common.db;

import java.util.List;

public interface MapperInter<T,C> {


    <RS> RS mappingObj(T t,C c);

    <RS> List<RS> mappingList(List<T> t);

}
