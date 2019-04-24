package com.netty.test.pojo.table;

import com.netty.test.proto.BaseMsg;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResTableData extends BaseMsg {

    /**
     * 表数据
     */
    private String tableData;

}
