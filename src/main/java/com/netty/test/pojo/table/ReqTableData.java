package com.netty.test.pojo.table;

import lombok.Data;

@Data
public class ReqTableData {

    private long id;

    private String tableName;

    @Override
    public String toString() {
        return "ReqTableData{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
