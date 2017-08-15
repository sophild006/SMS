package com.sms.demo.contact.sms.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class Msg {

    private List<ReceiverMsg> msgs;

    public List<ReceiverMsg> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<ReceiverMsg> msgs) {
        this.msgs = msgs;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "msgs=" + msgs +
                '}';
    }
}
