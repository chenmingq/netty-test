package com.netty.test.server;

import com.netty.test.annotation.ScanMapping;
import com.netty.test.proto.CommonConst;
import com.netty.test.utils.ClassUtil;
import com.netty.test.utils.LordPropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
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
    public static Integer PORT = null;


    /**
     * 初始化系统服务配置
     */
    public void initSysProperties(Class<?> clazz) {
        Properties properties = LordPropertiesUtils.lordProperties(CommonConst.PROPERTIES_NAME);
        if (null == properties) {
            LOG.error("{}", "初始化配置为空");
            return;
        }
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            Object k = entry.getKey();
            Object v = entry.getValue();
            if (null == k || null == v) {
                continue;
            }
            String key = (String) k;
            String value = (String) v;
            switch (key) {
                case "server.port":
                    PORT = Integer.parseInt(value);
                    break;
                case "serializer.impl.class":
                    CommonConst.SERIALIZER_IMPL_CLASS = value;
                    break;
                default:
                    break;
            }
        }
        scanMapping(clazz);
        LOG.info("{}", "加载服务配置成功");
    }

    /**
     * 扫描包路径
     *
     * @param clazz
     */
    private void scanMapping(Class<?> clazz) {
        ScanMapping scanMapping = clazz.getAnnotation(ScanMapping.class);
        String name;
        if (null != scanMapping) {
            name = scanMapping.name();
        } else {
            Package aPackage = clazz.getPackage();
            name = aPackage.getName();
        }
        ClassUtil.initReqMappingClazz(name);
    }

}
