package com.sms.demo.Util;

import com.sms.demo.contact.sms.bean.ReceiverMsg;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */

public class Util {

    public static void ListSort(ArrayList<ReceiverMsg> list) {
        Collections.sort(list, new Comparator<ReceiverMsg>() {
            /**
             *
             * @param lhs
             * @param rhs
             * @return an integer < 0 if lhs is less than rhs, 0 if they are
             *         equal, and > 0 if lhs is greater than rhs,比较数据大小时,这里比的是时间
             */
            @Override
            public int compare(ReceiverMsg lhs, ReceiverMsg rhs) {
                Date date1 = stringToDate(lhs.getDate());
                Date date2 = stringToDate(rhs.getDate());
                // 对日期字段进行升序，如果欲降序可采用after方法
                if (date1.before(date2)) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd hh:mm:ss");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    public static String generateDate(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "MM-dd hh:mm:ss");
        Date d = new Date(time);
        return dateFormat.format(d);
    }

    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static long stringToLong(String strTime) {
        Date date = stringToDate(strTime); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
