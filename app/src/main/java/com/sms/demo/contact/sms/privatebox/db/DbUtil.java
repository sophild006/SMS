package com.sms.demo.contact.sms.privatebox.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sms.demo.Util.GlobalContext;
import com.sms.demo.Util.Util;
import com.sms.demo.contact.sms.bean.ReceiverMsg;
import com.sms.demo.contact.sms.privatebox.bean.ThreadIdMsg;
import com.sms.demo.contact.sms.util.SmsUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10.
 */

public class DbUtil {


    private DbHelper helper;
    private SQLiteDatabase db;
    private static DbUtil mInstance;

    public static DbUtil getInstance() {
        if (mInstance == null) {
            mInstance = new DbUtil();
        }
        return mInstance;
    }

    public DbUtil() {
        if (GlobalContext.getAppContext() == null) {
            throw new IllegalArgumentException("context is null");
        }
        init();
    }

    public void init() {
        helper = new DbHelper(GlobalContext.getAppContext());
        db = helper.getReadableDatabase();
    }

    public void savePrivate(ThreadIdMsg ThreadIdMsg) {
        if (db == null) {
            init();
        }
        try {
            db.execSQL(
                    "INSERT INTO " + DbConfig.TABLE_NAME
                            + " (" +
                            DbConfig.THREAD_ID + "," +
                            DbConfig.ADDRESS + "," +
                            DbConfig.DATE + "," +
                            DbConfig.READ + "," +
                            DbConfig.STATUS + "," +
                            DbConfig.TYPE + "," +
                            DbConfig.BODY +
                            ")"
                            + "VALUES(?,?,?,?,?,?,?)",
                    new Object[]{
                            Integer.parseInt(ThreadIdMsg.getThread_id()),
                            ThreadIdMsg.getAddress(),
                            ThreadIdMsg.getDate(),
                            Integer.parseInt(ThreadIdMsg.getRead()),
                            Integer.parseInt(ThreadIdMsg.getStatus()),
                            Integer.parseInt(ThreadIdMsg.getType()),
                            ThreadIdMsg.getBody()
                    });

        } catch (Exception e) {

        } finally {
        }
    }

    public List<ThreadIdMsg> getSms() {
        if (db == null) {
            init();
        }
        List<ThreadIdMsg> mList = null;
        Cursor cursor = null;
        Map<Integer, String> map = new HashMap<>();
        try {
            cursor = db.rawQuery("select * from " + DbConfig.TABLE_NAME + " order by date desc", null);
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                mList = new ArrayList<>();
                for (int m = 0; m < cursor.getCount(); m++) {
                    ThreadIdMsg threadIdMsg = new ThreadIdMsg();
                    threadIdMsg.setThread_id(cursor.getInt(cursor.getColumnIndex(DbConfig.THREAD_ID)) + "");
                    threadIdMsg.setAddress(cursor.getString(cursor.getColumnIndex(DbConfig.ADDRESS)));
                    threadIdMsg.setBody(cursor.getString(cursor.getColumnIndex(DbConfig.BODY)));
                    threadIdMsg.setType(cursor.getInt(cursor.getColumnIndex(DbConfig.TYPE)) + "");
                    threadIdMsg.setDate(cursor.getString(cursor.getColumnIndex(DbConfig.DATE)));
                    if (map.containsKey(cursor.getInt(cursor.getColumnIndex(DbConfig.THREAD_ID)))) {
                        continue;
                    } else {
                        mList.add(threadIdMsg);
                    }
                    map.put(cursor.getInt(cursor.getColumnIndex(DbConfig.THREAD_ID)), "");
                    cursor.moveToNext();
                }
                return mList;
            } else {
                Log.d("wwq", "cursor is null");
            }
        } catch (Exception e) {
            Log.d("wwq", "e: " + e.getLocalizedMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
                map.clear();
            }
        }
        return null;
    }

    public boolean isPrivatePerson(String phoneNumber) {
        if (db == null) {
            init();
        }
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + DbConfig.TABLE_PRIVATE_NAME + " WHERE " + DbConfig.ADDRESS + "=?",
                    new String[]{phoneNumber});
            if (cursor != null && cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return false;
    }

    public void addPrivatePerson(String phoneNumber) {
        if (db == null) {
            init();
        }
        try {

            db.execSQL(
                    "INSERT INTO " + DbConfig.TABLE_PRIVATE_NAME
                            + " (" +
                            DbConfig.ADDRESS +
                            ")"
                            + "VALUES(?)",
                    new Object[]{
                            phoneNumber
                    });
        } catch (Exception e) {
            Log.d("wwq", "e: " + e.getLocalizedMessage());
        } finally {

        }

    }

}
