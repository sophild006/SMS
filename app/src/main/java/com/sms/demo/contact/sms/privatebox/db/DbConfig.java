package com.sms.demo.contact.sms.privatebox.db;

/**
 * Created by Administrator on 2017/8/10.
 */

public class DbConfig {
    //com.jb.zerosms.provider.C0920b
    public static final String DB_NAME = "sms.db";


    public static final String ID = "_id";
    public static final String THREAD_ID = "thread_id";
    public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String PROTOCOL = "protocol";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String REPLY_PATH_PRESENT = "reply_path_present";
    public static final String SUBJECT = "subject";
    public static final String BODY = "body";
    public static final String SERVICE_CENTER = "service_center";
    public static final String LOCKED = "locked";
    public static final String ERROR_CODE = "error_code";
    public static final String SEEN = "seen";
    public static final String SIM_ID = "sim_id";
    public static final String MODE = "mode";
    public static final String NET_DATE = "net_date";
    public static final String TABLE_NAME = "sms";
    public static final String TABLE_PRIVATE_NAME = "pri_person";
    public static final String CREATE_SQL = "CREATE TABLE sms (" +
            "_id INTEGER PRIMARY KEY," +
            "thread_id INTEGER," +
            "address TEXT," +
            "person INTEGER," +
            "date TEXT," +
            "protocol INTEGER," +
            "read INTEGER DEFAULT 0," +
            "status INTEGER DEFAULT -1," +
            "type INTEGER," +
            "reply_path_present INTEGER," +
            "subject TEXT," +
            "body TEXT," +
            "service_center TEXT," +
            "locked INTEGER DEFAULT 0," +
            "error_code INTEGER DEFAULT 0," +
            "seen INTEGER DEFAULT 0," +
            "sim_id INTEGER DEFAULT 0," +
            "mode INTEGER DEFAULT 0," +
            "net_date INTEGER);";

    public static final String CREATE_PRIVATE_SQL = "CREATE TABLE pri_person (" +
            "_id INTEGER PRIMARY KEY," +
            "address TEXT);";
}
