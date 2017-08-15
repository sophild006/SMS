package com.sms.demo.contact.sms.privatebox;

import android.os.Build;

/**
 * Created by Administrator on 2017/8/10.
 */

public class PwdUtil {
    public static final String[] strs = new String[]{"m9", "M9"};

    public static boolean isMiniScreen() {
        return isSpecialBrand(strs);
    }

    private static boolean isSpecialBrand(String[] strArr) {
        String str = Build.BOARD;
        if (str == null) {
            return false;
        }
        for (Object equals : strArr) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }
}
