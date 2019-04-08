package com.netty.test.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;


/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： 加载配置文件
 */

public class ServerProperties {


    private final static Logger LOG = LoggerFactory.getLogger(ServerProperties.class);

    private static ServerProperties instance = new ServerProperties();

    public static ServerProperties getInstance() {
        return instance;
    }

    public ServerProperties() {
    }


    /**
     * 端口
     */
    public static int PORT;

    private static InputStream IN;

    private static final String PROPERTIES_NAME = "application.properties";


    /**
     * 初始化系统服务配置
     */
    public void initSysProperties() {
        try {
            URL resource = Thread.currentThread().getContextClassLoader().getResource(PROPERTIES_NAME);
            if (null != resource) {
                Properties prop = new Properties();
                IN = resource.openStream();
                ///加载属性列表
                prop.load(IN);
                Iterator<String> it = prop.stringPropertyNames().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    switch (key) {
                        case "server.port":
                            PORT = Integer.parseInt(prop.getProperty(key));
                            break;
                        default:
                            break;
                    }
                }
                LOG.info("{}", "初始化服务配置完成");
            } else {
                LOG.error("加载配置文件失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != IN) {
                try {
                    IN.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
