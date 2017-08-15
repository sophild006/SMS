package com.sms.demo.contact.sms.send;

import com.sms.demo.contact.sms.bean.ReceiverMsg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class SendListenerManager {
    private ArrayList<onSendListener> mListenerList = new ArrayList<>();
    private static SendListenerManager mInstance;

    public static SendListenerManager getInstance() {
        if (mInstance == null) {
            mInstance = new SendListenerManager();
        }

        return mInstance;
    }

    public void addListener(onSendListener listener) {
        if (listener == null || mListenerList == null) {
            return;
        }
        mListenerList.add(listener);
    }

    public void removeSendListener(onSendListener listener) {
        if (listener == null || mListenerList == null) {
            return;
        }
        mListenerList.remove(listener);
    }

    public void sendListener(String phoneNumber, ArrayList<ReceiverMsg> msg) {

        for (onSendListener listener : mListenerList) {
            if (listener != null) {
                listener.onSendChanged(phoneNumber, msg);
            }
        }

    }

    private onSendListener mListener;

    public void setOnSendListener(onSendListener listener) {
        this.mListener = listener;
    }

    public interface onSendListener {
        void onSendChanged(String phoneNumber, ArrayList<ReceiverMsg> msg);
    }
}
