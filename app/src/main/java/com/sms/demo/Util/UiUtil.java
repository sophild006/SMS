package com.sms.demo.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;

/**
 * Created by Administrator on 2017/8/7.
 */

public class UiUtil {
    public static int dp2px(Context context, float f) {
        if (context == null) {
            return (int) ((((double) f) * 1.5d) + 0.5d);
        }
        return (int) ((context.getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    public static int Code(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

}
