package com.netty.test.pojo.table;

import com.netty.test.proto.BaseMsg;
import lombok.Data;

@Data
public class ReqTableData extends BaseMsg {

    private long id;

    private String tableName;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == 0) ? 0 : new Long(id).hashCode());
        result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
        result = prime * result + ((getCmdId() == 0) ? 0 : new Integer(getCmdId()).hashCode());
        result = prime * result + ((getModuleId() == 0) ? 0 : new Integer(getModuleId()).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ReqTableData)) {
            return false;
        }
        ReqTableData o1 = (ReqTableData) o;
        if (o1.getModuleId() != getCmdId()) {
            return false;
        } else if (o1.getCmdId() != getCmdId()) {
            return false;
        } else if (!o1.getTableName().equals(tableName)) {
            return false;
        } else if (o1.getId() != id) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "ReqTableData{" +
                "id=" + id +
                ", cmdId=" + getCmdId() +
                ", moduleId=" + getModuleId() +
                ", tableName='" + tableName +
                '}';
    }
}
