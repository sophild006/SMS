package com.sms.demo.contact.sms.util;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Patterns;
import android.widget.ListView;

import com.sms.demo.MainActivity;
import com.sms.demo.Util.GlobalContext;
import com.sms.demo.Util.Util;
import com.sms.demo.contact.sms.Config;
import com.sms.demo.contact.sms.Sms;
import com.sms.demo.contact.sms.bean.Msg;
import com.sms.demo.contact.sms.bean.ReceiverMsg;
import com.sms.demo.contact.sms.privatebox.bean.ThreadIdMsg;
import com.sms.demo.contact.sms.receiver.AppBroadcastReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SmsUtil {
    public static final Pattern NAME_ADDR_EMAIL_PATTERN = Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");

    /**
     * 输出Cursor结果集
     *
     * @param cursor
     */
    public static void printCursor(Cursor cursor) {
        java.text.DateFormat dateFormat = DateFormat.getDateFormat(GlobalContext.getAppContext());
        List<ThreadIdMsg> list = new ArrayList<>();
        try {
            if (cursor != null && cursor.getCount() > 0) {
                int columnCount;
                String columnName;
                String columnValue;
                Map<Integer, String> map = new HashMap<>();
                while (cursor.moveToNext()) {
                    // 获得行中所有的列的总数
                    columnCount = cursor.getColumnCount();
                    for (int i = 0; i < columnCount; i++) {
                        columnName = cursor.getColumnName(i);
                        columnValue = cursor.getString(i);
                        Log.i("wwq", "当前是第" + cursor.getPosition() + "行: " + columnName + " = " + columnValue + "  ");
                        map.put(i, cursor.getString(i));
                    }
                    Log.d("wwq", "map: " + map.toString());
                    ThreadIdMsg threadIdMsg = new ThreadIdMsg();
                    for (int m = 0; m < map.size(); m++) {
                        threadIdMsg.setThread_id(map.get(0));
                        threadIdMsg.setAddress(map.get(1));
                        threadIdMsg.setDate(map.get(2));
                        threadIdMsg.setBody(map.get(3));
                        threadIdMsg.setType(map.get(4));
                        threadIdMsg.setStatus(map.get(6));
                        threadIdMsg.setRead(map.get(7));
                    }
                    list.add(threadIdMsg);
                }
                cursor.moveToPosition(-1);
            }
        } catch (Exception e) {

        } finally {
            GlobalContext.setThreadsMsg(list);
        }

    }


    /**
     * 根据号码得到联系人的姓名
     *
     * @param resolver
     * @param phoneNumber
     * @return
     */
    public static String getContactName(ContentResolver resolver, String phoneNumber) {
        try {
            // content://com.android.contacts/phone_lookup/95555
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
            Cursor cursor = resolver.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String contactName = cursor.getString(0);
                cursor.close();
                return contactName;
            }
        } catch (Exception e) {
            Log.e("wwq", e.getLocalizedMessage());
        }
        return null;
    }

//    public static int getThread_id(ContentResolver resolver, String phoneNumber) {
//        try {
//            // content://com.android.contacts/phone_lookup/95555
//            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
//            Cursor cursor = resolver.query(uri, new String[]{"thread_id"}, null, null, null);
//
//            if (cursor != null && cursor.moveToFirst()) {
//                int thread_id = cursor.getInt(0);
//                cursor.close();
//                return thread_id;
//            }
//        } catch (Exception e) {
//            Log.e("wwq", e.getLocalizedMessage());
//        }
//        return 0;
//    }


    /**
     * 根据号码得到联系人的头像
     *
     * @param resolver
     * @param address
     * @return
     */
    public static Bitmap getContactIcon(ContentResolver resolver, String address) {
        // 根据号码取得联系人的id
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(address));
        Cursor cursor = resolver.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone._ID}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            long contactID = cursor.getLong(0);
            cursor.close();
            // 根据联系人的id得到联系人的头像
            uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID);
            InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);

            Bitmap contactIcon = BitmapFactory.decodeStream(is);
            return contactIcon;
        }
        return null;
    }

    /**
     * 根据索引返回对应的uri
     *
     * @param position 0 代表是收件箱
     *                 1 代表是发件箱
     *                 2 代表是已发送
     *                 3 代表是草稿箱
     * @return
     */
    public static Uri getTypeUri(int position) {
        switch (position) {
            case 0:
                return Sms.INBOX_URI;
            case 1:
                return Sms.OUTBOX_URI;
            case 2:
                return Sms.SENT_URI;
            case 3:
                return Sms.DRAFT_URI;
            case 4:
                return Sms.SMS_URI;
            default:
                break;
        }
        return null;
    }

    public static List<ReceiverMsg> getSmsInPhone(Context context, Uri uri) {
        List<ReceiverMsg> msgs = new ArrayList<>();
        try {
            ContentResolver cr = context.getContentResolver();
            String[] projection = new String[]{"_id", "address", "person",
                    "body", "date", "type", "thread_id", "status", "protocol"};
            Cursor cur = cr.query(uri, projection, null, null, "date desc");
            if (cur.moveToFirst()) {
                String _name;
                String _phoneNumber;
                String _smsbody;
                String _date;
                int _status = 0;
                int _protocol = -2;
                int _thread_id = 0;
                //
                int nameColumn = cur.getColumnIndex("person");
                int phoneNumberColumn = cur.getColumnIndex("address");
                int smsbodyColumn = cur.getColumnIndex("body");
                int dateColumn = cur.getColumnIndex("date");
                int typeColumn = cur.getColumnIndex("type");
                int typeProtocol = cur.getColumnIndex("protocol");
                int status = cur.getColumnIndex("status");
                int thread_id = cur.getColumnIndex("thread_id");
                do {
                    ReceiverMsg msg = new ReceiverMsg();
                    _name = cur.getString(nameColumn);
                    _phoneNumber = cur.getString(phoneNumberColumn);
                    _smsbody = cur.getString(smsbodyColumn);
                    if (typeProtocol != -1) {
                        _protocol = cur.getInt(typeProtocol);
                    }
                    if (thread_id != -1) {
                        _thread_id = cur.getInt(thread_id);
                    }
                    if (status != -1) {
                        _status = cur.getInt(status);
                    }
                    int typeId = cur.getInt(typeColumn);
                    Log.d("wwq", "_name: " + _name + "\n");
                    if (TextUtils.isEmpty(_name)) {
                        _name = SmsUtil.getContactName(context.getContentResolver(), _phoneNumber);
                    }
                    msg.setPerson(_name + "");
                    msg.setAddress(_phoneNumber);
                    Bitmap bitmap = getContactIcon(GlobalContext.getAppContext().getContentResolver(), _phoneNumber);
                    if (bitmap != null) {
                        msg.setBitmap(getBytes(bitmap));
                    }
                    msg.setBody((_smsbody != null) ? _smsbody : "");
                    msg.setDate(Util.generateDate(cur.getLong(dateColumn)));
                    msg.setProtocol(_protocol);
                    msg.setType(typeId);
                    msg.setStatus(_status);
                    msg.setThread_id(_thread_id);
                    msgs.add(msg);
                } while (cur.moveToNext());
            } else {

            }
            return msgs;
        } catch (SQLiteException ex) {
            Log.d("wwq", ex.getMessage());
        } finally {
//            Log.d("wwq", "msgs: " + msgs.toString());
        }
        return null;
    }

    /**
     * bitmap 转byte
     *
     * @param bitmap
     * @return
     */
    public static byte[] getBytes(Bitmap bitmap) {
        //实例化字节数组输出流
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
            bytes = baos.toByteArray();
        } catch (Exception E) {

        } finally {
            if (baos != null) {
                try {
                    baos.close();
                    baos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;//创建分配字节数组
    }

    /**
     * byte 转bitmap
     *
     * @param data
     * @return
     */
    public static Bitmap getBitmap(byte[] data) {
        Bitmap bitmap = null;
        if (data == null) {
            return null;
        }
        try {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);//从字节数组解码位图
        } catch (Exception E) {
        }
        return bitmap;
    }

    private static Method method;

    public static synchronized boolean isDefaultSms(Context context) {
        String str;
        try {
            if (method == null) {
                method = Class.forName("android.provider.Telephony$Sms").getMethod("getDefaultSmsPackage", new Class[]{Context.class});
            }
            str = (String) method.invoke(null, new Object[]{context});
            if (!TextUtils.isEmpty(str) && str.equalsIgnoreCase(context.getPackageName())) {
                return true;
            }
        } catch (Throwable th) {
            return false;
        }
        return false;
    }

    public static boolean isNubia() {
        return Build.BRAND != null && Build.BRAND.equalsIgnoreCase("nubia");
    }

    public static float isFlyme() {
        float f = -1.0f;
        if (isMeizu()) {
            try {
                String str = Build.DISPLAY;
                if (str.startsWith("Flyme OS ")) {
                    f = Float.valueOf(str.substring("Flyme OS ".length(), "Flyme OS ".length() + 3)).floatValue();
                }
            } catch (Throwable th) {
            }
        }
        return f;
    }

    public static boolean isMeizu() {
        return Build.PRODUCT.toLowerCase().contains("meizu") || Build.BRAND.equals("Meizu");
    }

    public static boolean isHisense() {
        try {
            if (Build.BRAND.equalsIgnoreCase("Hisense")) {
                return true;
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public static boolean Code() {
        if (isHisense()) {
            return false;
        }
        if ((!isMeizu() || isFlyme() >= 4.2f) && isNubia() && Build.VERSION.SDK_INT >= 19) {
            return true;
        }
        return false;
    }

    public static void setDefaultSms(Context context) {
        if (!Code()) {
            try {
                Intent intent =
                        new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                        context.getPackageName());
                intent.setFlags(268435456);
                context.startActivity(intent);
            } catch (Exception e) {

            }
        }
    }

    /**
     * 手否是email
     *
     * @param str
     * @return
     */
    public static boolean isEmailAddress(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(extractAddrSpec(str)).matches();
    }

    public static String extractAddrSpec(String str) {
        Matcher matcher = NAME_ADDR_EMAIL_PATTERN.matcher(str);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return str;
    }

    /**
     * 获取无效的接收者
     *
     * @param list
     * @param z
     * @return
     */
    public static String getInvalidPhone(List<String> list, boolean z) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : list) {
            if (!isValid(str)) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(str);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 是否有效（号码，邮箱）
     *
     * @param str
     * @return
     */
    public static boolean isValid(String str) {
        return PhoneNumberUtils.isWellFormedSmsAddress(str) || isEmailAddress(str);
    }

    public static int getContactID(ContentResolver resolver, Uri uri) {
        Cursor cursor = resolver.query(uri, new String[]{"has_phone_number", "_id"}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int hasPhoneNumber = cursor.getInt(0);
            if (hasPhoneNumber > 0) {
                int contactID = cursor.getInt(1);
                cursor.close();
                return contactID;
            }
        }
        return -1;
    }

    /**
     * 根据联系人的id取联系人的手机号
     *
     * @param resolver
     * @param contact_id
     * @return
     */
    public static String getContactAddress(ContentResolver resolver, int contact_id) {
        String selection = "contact_id = ?";
        String[] selectionArgs = {String.valueOf(contact_id)};
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(0);
            cursor.close();
            return address;
        }
        return null;
    }


    /**
     * 发送短信
     *
     * @param address
     * @param content
     */
    public static void sendMessage(Context context, String address, String content) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> divideMessage = smsManager.divideMessage(content);
        Intent intent = new Intent("com.go.sms.receive.SendMessageBroadcastReceive");
        PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        for (String text : divideMessage) {
            smsManager.sendTextMessage(
                    address,    // 对方手机号
                    null,    // 短信中心号码
                    text,
                    sentIntent,    // 当短信发送成功时回调, 回调方式: 延期意图(延期意图指向的是广播接收者)
                    null);    // 当对方收到短信时回调
        }

        // 把整条短信添加到数据库
        writeMessage(context, address, content);
    }

    private static void writeMessage(Context context, String address, String content) {
        ContentValues values = new ContentValues();
        values.put("address", address);
        values.put("body", content);
        context.getContentResolver().insert(Sms.SENT_URI, values);
    }


    public static void queryContactPhoneNumber() {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = GlobalContext.getAppContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            Log.d("wwq", "name " + name + "  number: " + number);
        }
    }
}
