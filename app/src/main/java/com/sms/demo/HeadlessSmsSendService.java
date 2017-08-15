package com.sms.demo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;

/**
 * Created by Administrator on 2017/8/7.
 */
public class HeadlessSmsSendService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public HeadlessSmsSendService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
