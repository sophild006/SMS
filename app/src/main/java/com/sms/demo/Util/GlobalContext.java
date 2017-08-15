package com.sms.demo.Util;

import android.content.Context;

import com.sms.demo.contact.sms.privatebox.bean.ThreadIdMsg;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public class GlobalContext {
    private GlobalContext() {
    }

    private static Context sAppContext;
    private static List<ThreadIdMsg> mMsg;

    public static void setThreadsMsg(List<ThreadIdMsg> msg) {
        mMsg = msg;
    }

    public static List<ThreadIdMsg> getmMsg() {
        return mMsg;
    }


    public static final void setAppContext(Context appContext) {
        if (appContext != null) {
            sAppContext = appContext.getApplicationContext();
        }
    }

    public static final Context getAppContext() {
        assert sAppContext != null;
        return sAppContext;
    }
}
