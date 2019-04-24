package com.netty.test.controller.login;

import com.netty.test.annotation.ReqMapping;
import com.netty.test.pojo.proto.NettyTest;
import com.netty.test.pojo.table.ReqTableData;

@ReqMapping(id = NettyTest.MESSAGE_TYPE.QUERY_TABLE_VALUE)
public class ReqQueryTableDataController {

    @ReqMapping(id = NettyTest.MESSAGE_TYPE.REQ_QUERY_TABLE_DATA_VALUE)
    public void reqQuery (ReqTableData reqTableData){
        System.out.println(reqTableData);

    }

}
