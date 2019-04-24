package com.netty.test.common.manager;

import com.netty.test.server.Session;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private volatile Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public void putSession(String k, Session session) {
        sessionMap.put(k, session);
    }

    public Session getSession(String k) {
        boolean b = sessionMap.containsKey(k);
        if (!b) {
            return null;
        }
        return sessionMap.get(k);
    }


}
