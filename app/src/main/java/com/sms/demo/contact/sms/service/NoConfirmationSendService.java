package com.sms.demo.contact.sms.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


/* compiled from: ZeroSms */
public class NoConfirmationSendService extends IntentService {
    public NoConfirmationSendService() {
        super(NoConfirmationSendService.class.getName());
        setIntentRedelivery(true);
    }

    protected void onHandleIntent(Intent intent) {
        log("NoConfirmationSendService onHandleIntent");
        String action = intent.getAction();
        if ("android.intent.action.RESPOND_VIA_MESSAGE".equals(action)) {
            Bundle extras = intent.getExtras();
            if (extras == null) {
                log("Called to send SMS but no extras");
                return;
            }
        }
        log("NoConfirmationSendService onHandleIntent wrong action: " + action);
    }

    public static void log(String str) {
        Thread currentThread = Thread.currentThread();
        long id = currentThread.getId();
        Log.d("wwq", "[" + id + "] [" + currentThread.getStackTrace()[3].getMethodName() + "] " + str);
    }
}