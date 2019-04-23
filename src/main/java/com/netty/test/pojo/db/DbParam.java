package com.netty.test.pojo.db;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : chenmq
 * date : 2019-4-18
 * Project : netty-test
 * Description：数据库连接参数
 */
@Data
public class DbParam {
    /**
     * 地址
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 库名
     */
    private String dbName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 完成的链接
     */
    private String url;

    /**
     * 数据库类型. [mysql，oracle]
     */
    private String dbType;

    /**
     * 编码类型
     */
    private String characterEncoding;

    public DbParam(String host, Integer port, String dbName, String userName, String password, String url, String dbType, String characterEncoding) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
        this.url = url;
        this.dbType = dbType;
        this.characterEncoding = characterEncoding;
    }

    public DbParam() {
    }

    /**
     * 检查当前的对象是否为空
     *
     * @return
     */
    public boolean checkFieldsIsEmpty() {
        if (StringUtils.isEmpty(this.dbName)) {
            return false;
        }
        if (StringUtils.isEmpty(this.userName)) {
            return false;
        }
        if (StringUtils.isEmpty(this.password)) {
            return false;
        }
        if (StringUtils.isEmpty(this.host) || (null == this.port || this.port < 1)) {
            return !StringUtils.isEmpty(this.url);
        }
        return true;
    }

}
