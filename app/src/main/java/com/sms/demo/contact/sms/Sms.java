package com.sms.demo.contact.sms;

import android.net.Uri;

public class Sms {

    /**
     * 查询会话的uri
     */
    public static final Uri CONVERSATION_URI = Uri.parse("content://sms/conversations");

    /**
     * 操作sms表的uri
     */
    public static final Uri SMS_URI = Uri.parse("content://sms/");

    /**
     * 操作已发送的uri
     */
    public static final Uri SENT_URI = Uri.parse("content://sms/sent");

    /**
     * 操作收件箱的uri
     */
    public static final Uri INBOX_URI = Uri.parse("content://sms/inbox");

    /**
     * 操作发件箱的uri
     */
    public static final Uri OUTBOX_URI = Uri.parse("content://sms/outbox");

    /**
     * 操作草稿箱的uri
     */
    public static final Uri DRAFT_URI = Uri.parse("content://sms/draft");

    /**
     * 添加到groups表中的uri
     */
    public static final Uri GROUPS_INSERT_URI = Uri.parse("content://com.itheima23.smsmanager.provider.GroupContentProvider/groups/insert");

    /**
     * 查询groups表中所有数据的uri
     */
    public static final Uri GROUPS_QUERY_ALL_URI = Uri.parse("content://com.itheima23.smsmanager.provider.GroupContentProvider/groups/");

    /**
     * 修改groups表数据的uri
     */
    public static final Uri GROUPS_UPDATE_URI = Uri.parse("content://com.itheima23.smsmanager.provider.GroupContentProvider/groups/update");

    /**
     * 删除groups表数据的uri(单条, 在uri中指定id)
     */
    public static final Uri GROUPS_DELETE_URI = Uri.parse("content://com.itheima23.smsmanager.provider.GroupContentProvider/groups/delete/#");

    /**
     * 添加到thread_group表的uri
     */
    public static final Uri THREAD_GROUP_INSERT_URI = Uri.parse("content://com.itheima23.smsmanager.provider.GroupContentProvider/thread_group/insert");

    /**
     * 查询thread_group表中所有的数据
     */
    public static final Uri THREAD_GROUP_QUERY_ALL_URI = Uri.parse("content://com.itheima23.smsmanager.provider.GroupContentProvider/thread_group");

    // 短信的类型: 接收
    public static final int RECEIVE_TYPE = 1;
    // 短信的类型: 发送
    public static final int SEND_TYPE = 2;
}
