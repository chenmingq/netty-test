package com.netty.test.controller.login;


import com.netty.test.annotation.ReqMapping;

@ReqMapping(id = 1)
public class ReqLoginController {


    @ReqMapping(id = 1)
    public void ss(Object protoMsg) {
        System.out.println(protoMsg);
    }
}
