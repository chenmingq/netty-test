package com.netty.test.server;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description：
 */
@Slf4j
public class SessionManager {

    private static SessionManager instance = new SessionManager();

    public static SessionManager getInstance() {
        return instance;
    }

    public SessionManager() {
    }



}
