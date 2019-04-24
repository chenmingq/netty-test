package com.netty.test.proto;

import lombok.Data;

@Data
public class BaseMsg {
    /**
     * 模块id
     */
    private int moduleId;

    /**
     * 指定某个的子模块
     */
    private int cmdId;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cmdId == 0) ? 0 : new Integer(cmdId).hashCode());
        result = prime * result + ((moduleId == 0) ? 0 : new Integer(moduleId).hashCode());
        return result;
    }
}
