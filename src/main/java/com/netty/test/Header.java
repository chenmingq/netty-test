package com.netty.test;

/**
 * @author : chenmq
 * date : 2019-4-08
 * Project : netty-test
 * Description： 消息头
 */
public class Header {

    /**
     * 唯一通信标志
     */
    private int magic;

    /**
     * 模块id
     */
    private int moduleId;

    /**
     * 消息体
     */
    private byte[] body;

    /**
     * 当前时间戳
     */
    private transient long timestamp;


    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = System.currentTimeMillis();
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public String toString() {
        return "Header{" +
                "magic=" + magic +
                ", moduleId=" + moduleId +
                ", timestamp=" + timestamp +
                '}';
    }
}
