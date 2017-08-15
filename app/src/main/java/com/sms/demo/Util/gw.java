package com.sms.demo.Util;

import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ZeroSms */
public class gw {
    private static gw Code;
    private ArrayList f6474V = new ArrayList();

    private gw() {
    }

    public static synchronized gw Code() {
        gw gwVar;
        synchronized (gw.class) {
            if (Code == null) {
                Code = new gw();
            }
            gwVar = Code;
        }
        return gwVar;
    }

    public synchronized void m5215V() {
        this.f6474V.clear();
    }

    public synchronized void Code(String str, String str2, String str3) {
        Object obj = null;
        gx gxVar = null;
        Iterator it = this.f6474V.iterator();
        while (it.hasNext()) {
            gxVar = (gx) it.next();
            if (PhoneNumberUtils.compare(gxVar.Code, str)) {
                gxVar.f6476V = str2;
                gxVar.f6475I = str3;
                obj = 1;
                break;
            }
        }
        if (obj == null) {
            gxVar = new gx();
            gxVar.Code = str;
            gxVar.f6476V = str2;
            gxVar.f6475I = str3;
            this.f6474V.add(gxVar);
        }
    }

    public synchronized void Code(String str) {
        for (int i = 0; i < this.f6474V.size(); i++) {
            if (PhoneNumberUtils.compare(((gx) this.f6474V.get(i)).Code, str)) {
                this.f6474V.remove(i);
                break;
            }
        }
    }
}