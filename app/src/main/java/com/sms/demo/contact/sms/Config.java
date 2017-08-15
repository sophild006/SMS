package com.sms.demo.contact.sms;

/**
 * Created by Administrator on 2017/8/4.
 */

public class Config {
    public static final String[] SMS_PROJECTION = {
            "_id",
            "address",
            "body",
            "date",
            "type"
    };
    public static final int ADDRESS_COLUMN_INDEX = 1;
    public static final int BODY_COLUMN_INDEX = 2;
    public static final int DATE_COLUMN_INDEX = 3;
    public static final int TYPE_COLUMN_INDEX = 4;

}
