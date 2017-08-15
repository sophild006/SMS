package com.sms.demo.contact.sms.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/4.
 */

public class ReceiverMsg implements Serializable {

    private byte[] bitmap;
    /**
     * body
     */
    private String body;
    /**
     * 对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
     */
    private int thread_id;
    /**
     * 日期
     */
    private String date;
    /**
     * 短信类型1是接收到的，2是已发出
     */
    private int type;
    /**
     * 短信状态-1接收，0 complete,64 pending,128 failed
     */
    private int status;
    /**
     * read：是否阅读0未读，1已读
     */
    private int read;
    /**
     * protocol：协议0 SMS_RPOTO短信，1 MMS_PROTO彩信
     */
    private int protocol;
    /**
     * 发件人地址，即手机号，如+8613811810000
     */
    private String address;
    /**
     * person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
     */
    private String person;

    /**
     * nei xing
     */
    private int msgtype;
    /**
     * 是否选中
     */
    private boolean isSecleted;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(int msgtype) {
        this.msgtype = msgtype;
    }

    public boolean isSecleted() {
        return isSecleted;
    }

    public void setSecleted(boolean secleted) {
        isSecleted = secleted;
    }

    public byte[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(byte[] bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return "ReceiverMsg{" +
                "body='" + body + '\'' +
                ", thread_id=" + thread_id +
                ", date='" + date + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", read=" + read +
                ", protocol=" + protocol +
                ", address='" + address + '\'' +
                ", person='" + person + '\'' +
                ", msgtype=" + msgtype +
                ", isSecleted=" + isSecleted +
                '}';
    }
}
