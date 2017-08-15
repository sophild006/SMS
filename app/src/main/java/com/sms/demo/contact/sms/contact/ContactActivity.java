package com.sms.demo.contact.sms.contact;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ContactData> listMembers = new ArrayList<>();
        getAllContact(listMembers);
    }

    private static final String[] MYPROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY, ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    /**
     * see http://blog.chinaunix.net/uid-26930580-id-4137246.html
     *
     * @param listMembers
     */
    public void getAllContact(List<ContactData> listMembers) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, MYPROJECTION,
                null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
        try {

            if (cursor.moveToFirst()) {
                do {
                    ContactData contact = new ContactData();
                    String contact_phone = cursor.getString(2);
                    String name = cursor.getString(0);
                    String sortKey = getSortKey(cursor.getString(1));
                    int contact_id = cursor.getInt(3);
                    contact.name = name;
                    contact.sortKey = sortKey;
                    contact.phone = contact_phone;
                    contact.setId(contact_id);
                    if (name != null)
                        listMembers.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            Log.d("wwq", "list:  " + listMembers.toString());
        }
    }

    /**
     * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
     *
     * @param sortKeyString 数据库中读取出的sort key
     * @return 英文字母或者#
     */
    private static String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }
}
