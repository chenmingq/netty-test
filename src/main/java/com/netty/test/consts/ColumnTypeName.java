package com.netty.test.consts;

import java.util.Arrays;

public interface ColumnTypeName {

    /**
     * ①TinyBlob类型  最大能容纳255B的数据
     * ②Blob类型  最大能容纳65KB的
     * ③MediumBlob类型  最大能容纳16MB的数据
     * ④LongBlob类型  最大能容纳4GB的数据
     */
    enum MYSQL_COLUMN_TYPE_NAME {
        /**
         * TinyBlob类型  最大能容纳255B的数据
         */
        TinyBlob("TinyBlob"),
        /**
         * 最大能容纳65KB的
         */
        Blob("Blob"),
        /**
         * 最大能容纳16MB的数据
         */
        MediumBlob("MediumBlob"),
        /**
         * 最大能容纳4GB的数据
         */
        LongBlob("LongBlob"),
        ;

        private String name;

        public String getName() {
            return name;
        }

        MYSQL_COLUMN_TYPE_NAME(String name) {
            this.name = name;
        }

        public static MYSQL_COLUMN_TYPE_NAME parse(String name) {
            return Arrays.stream(MYSQL_COLUMN_TYPE_NAME.values()).filter(v -> name.equalsIgnoreCase(v.getName())).findFirst().orElse(null);
        }
    }
}
