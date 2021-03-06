package com.netty.test.controller.login;

import com.netty.test.annotation.ParamName;
import com.netty.test.annotation.ReqMapping;
import com.netty.test.pojo.proto.NettyTest;
import com.netty.test.pojo.table.ReqTableData;
import com.netty.test.pojo.table.ResTableData;
import com.netty.test.proto.MessageProcessHelper;

@ReqMapping(id = NettyTest.MESSAGE_TYPE.QUERY_TABLE_VALUE)
public class ReqQueryTableDataController {

    @ReqMapping(id = NettyTest.MESSAGE_TYPE.REQ_QUERY_TABLE_DATA_VALUE)
    public void reqQuery(ReqTableData reqTableData, @ParamName(name = "id") Object id) {

        System.out.printf("服务端收到消息 -> reqTableData=> %s id=> %s \n\r", reqTableData, id);


        int msgId = NettyTest.MESSAGE_TYPE.RES_QUERY_TABLE_DATA_VALUE;
        int moduleId = NettyTest.MESSAGE_TYPE.QUERY_TABLE_VALUE;
        ResTableData resTableData = new ResTableData();
        resTableData.setTableData("欢迎来撩我啊 ^_^");
        resTableData.setCmdId(msgId);
        resTableData.setModuleId(moduleId);
        MessageProcessHelper.getInstance().responseExecute(resTableData);
    }

}
