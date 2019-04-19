package com.netty.test.pojo.db;

import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

/**
 * @author : chenmq
 * date : 2019-4-17
 * Project : netty-test
 * Description：数据库中的表的信息
 */
@Data
public class InformationSchemaTables {
    /**
     * ----+--------------------+----------+----------------+---------------+
     * | TABLE_CATALOG | TABLE_SCHEMA | TABLE_NAME | TABLE_TYPE | ENGINE | VERSION | RO
     * W_FORMAT | TABLE_ROWS | AVG_ROW_LENGTH | DATA_LENGTH | MAX_DATA_LENGTH | INDEX_L
     * ENGTH | DATA_FREE | AUTO_INCREMENT | CREATE_TIME         | UPDATE_TIME | CHECK_T
     * IME | TABLE_COLLATION    | CHECKSUM | CREATE_OPTIONS | TABLE_COMMENT |
     */

    /**
     * 表所属目录的名称。
     */
    private String tableCatalog;

    /**
     * 表所属的模式（数据库）的名称。
     */
    private String tableSchema;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表类型[system view|base table]
     */
    private String tableType;

    /**
     * 使用的数据库引擎[MyISAM|CSV|InnoDB]
     */
    private String engine;

    /**
     * 版本，默认值10
     */
    private BigInteger version;

    /**
     * 行格式[Compact|Dynamic|Fixed]
     */
    private String rowFormat;

    /**
     * 表里所存多少行数据
     */
    private BigInteger tableRows;

    /**
     * 平均行长度
     */
    private BigInteger avgRowLength;

    /**
     * 数据长度(byte)
     */
    private BigInteger dataLength;

    /**
     * 最大数据长度
     */
    private BigInteger maxDataLength;

    /**
     * 索引长度
     */
    private BigInteger indexLength;

    /**
     * 空间碎片
     */
    private BigInteger dataFree;

    /**
     * 做自增主键的自动增量当前值
     */
    private BigInteger autoIncrement;

    /**
     * 表的创建时间
     */
    private Date createTime;

    /**
     * 表的更新时间
     */
    private Date updateTime;

    /**
     * 表的检查时间
     */
    private Date checkTime;

    /**
     * 表的字符校验编码集
     */
    private String tableCollation;

    /**
     * 校验和
     */
    private BigInteger checksum;

    /**
     * 创建选项
     */
    private String createOptions;

    /**
     * 表的注释、备注
     */
    private String tableComment;

    @Override
    public String toString() {
        return "InformationSchemaTables{" +
                "tableCatalog=" + tableCatalog +
                ", tableSchema=" + tableSchema +
                ", tableName=" + tableName +
                ", tableType=" + tableType +
                ", engine=" + engine +
                ", version=" + version +
                ", rowFormat=" + rowFormat +
                ", tableRows=" + tableRows +
                ", avgRowLength=" + avgRowLength +
                ", dataLength=" + dataLength +
                ", maxDataLength=" + maxDataLength +
                ", indexLength=" + indexLength +
                ", dataFree=" + dataFree +
                ", autoIncrement=" + autoIncrement +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", checkTime=" + checkTime +
                ", tableCollation=" + tableCollation +
                ", checksum=" + checksum +
                ", createOptions=" + createOptions +
                ", tableComment=" + tableComment +
                '}';
    }
}
