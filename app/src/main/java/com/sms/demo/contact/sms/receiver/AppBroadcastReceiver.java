package com.sms.demo.contact.sms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AppBroadcastReceiver extends BroadcastReceiver {
    public static final String DATA_SCHEME = "package";

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String dataString = intent.getDataString();
            Log.d("wwq", "dataString: " + dataString);
            int indexOf = dataString.indexOf("package:");
            if (indexOf >= 0) {
                dataString = dataString.substring(indexOf + "package:".length());
            }

        }
    }
}