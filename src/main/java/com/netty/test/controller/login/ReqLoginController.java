package com.netty.test.controller.login;


import com.netty.test.annotation.ReqMapping;
import com.netty.test.pojo.proto.NettyTest;

@ReqMapping(id = NettyTest.MESSAGE_TYPE.LOGIN_VALUE)
public class ReqLoginController {


    @ReqMapping(id = NettyTest.MESSAGE_TYPE.REQ_LOGIN_VALUE)
    public void ss(Object protoMsg) {
        NettyTest.ReqLogin reqLogin = (NettyTest.ReqLogin) protoMsg;
    }
}
