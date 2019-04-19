package com.netty.test;


import com.netty.test.server.Server;
import com.netty.test.server.ServerProperties;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： netty服务启动类
 */
public class ServerStart {


    public static void main(String[] args) {
        ServerProperties.getInstance().initSysProperties();
        Server.initServer();
    }


}
