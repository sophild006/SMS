package com.sms.demo;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.sms.demo.Util.GlobalContext;

/**
 * Created by Administrator on 2017/8/3.
 */

public class MyApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalContext.setAppContext(this);
    }
}
