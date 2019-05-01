package com.netty.test.proto;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： 公共常量
 */
public class CommonConst {

    /**
     * 唯一通信标志
     */
    public final static int MAGIC_CODE = 0x0CAFFEE0;

    /**
     * 消息头长度
     */
    public final static int HEAD_LENGTH = 0x16;

    /**
     * 消息序列化实现类
     */
    public static String SERIALIZER_IMPL_CLASS;

    /**
     * 配置文件名称
     */
    public static final String PROPERTIES_NAME = "application.properties";

    /**
     * 数据库连接池配置文件名称
     */
    public static final String DB_SERVER_PROPERTIES = "db_server.properties";

    /**
     *  mapper 操作对象对象地址
     */
    public static final String MAPPER_IMPLEMENTS_CLASS_NAME = "com.netty.test.common.db.MapperImpl";


}
