package com.sms.demo.contact.sms.util;

import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

public class SendContactManager {
    private static SendContactManager mInstance;
    private ArrayList contactList = new ArrayList();

    private SendContactManager() {
    }

    public static synchronized SendContactManager getmInstance() {
        synchronized (SendContactManager.class) {
            if (mInstance == null) {
                mInstance = new SendContactManager();
            }
        }
        return mInstance;
    }

    public synchronized void clear() {
        this.contactList.clear();
    }

    public synchronized void addContact(String str, String str2, String str3) {
        Object obj = null;
        ContactBean gxVar = null;
        Iterator it = this.contactList.iterator();
        while (it.hasNext()) {
            gxVar = (ContactBean) it.next();
            if (PhoneNumberUtils.compare(gxVar.phoneName, str)) {
                gxVar.f6476V = str2;
//                gxVar.f6475I = str3;
                obj = 1;
                break;
            }
        }
        if (obj == null) {
            gxVar = new ContactBean();
            gxVar.phoneName = str;
            gxVar.f6476V = str2;
//            gxVar.f6475I = str3;
            this.contactList.add(gxVar);
            Log.d("wwq", "str: " + str + " str2: " + str2 + "  str3:" + str3);
        }
    }

    public synchronized void removeContact(String str) {
        for (int i = 0; i < this.contactList.size(); i++) {
            if (PhoneNumberUtils.compare(((ContactBean) this.contactList.get(i)).phoneName, str)) {
                this.contactList.remove(i);
                break;
            }
        }
    }

    public class ContactBean {
        public String phoneName;
        public String phoneNumber;
        public String f6476V;

        public ContactBean generateContact() {
            ContactBean bean = new ContactBean();
//            bean.f6476V = this.f6476V;
            bean.phoneName = this.phoneName;
            bean.phoneNumber = this.phoneNumber;
            return bean;
        }
    }
}