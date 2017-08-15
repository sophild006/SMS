package com.sms.demo.contact.sms.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int resultCode = getResultCode();
        Log.d("wwq", "code: "+resultCode);
        if (resultCode == Activity.RESULT_OK) {
            Log.d("wwq", "发送成功");
        } else {
            Log.d("wwq", "发送失败");
        }
    }
}


