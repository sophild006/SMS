package com.sms.demo.contact.sms.privatebox.bean;

/**
 * Created by Administrator on 2017/8/10.
 */

public class ThreadIdMsg {
    public String thread_id;
    public String address;
    public String date;
    public String body;
    public String read;
    //    public int count
    public String type;
    private String status;
    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "ThreadIdMsg{" +
                "thread_id=" + thread_id +
                ", address='" + address + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", read='" + read + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
