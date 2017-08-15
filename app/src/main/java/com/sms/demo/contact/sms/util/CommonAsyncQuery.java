package com.sms.demo.contact.sms.util;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;

/**
 * Created by Administrator on 2017/8/4.
 */

public class CommonAsyncQuery extends AsyncQueryHandler {

    private OnQueryCompleteListener mOnQueryCompleteListener;

    public CommonAsyncQuery(ContentResolver cr, OnQueryCompleteListener listener) {
        this(cr);
        this.mOnQueryCompleteListener = listener;
    }

    public CommonAsyncQuery(ContentResolver cr) {
        super(cr);
    }


    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        super.onQueryComplete(token, cookie, cursor);
        SmsUtil.printCursor(cursor);    // 打印一下游标
        if (mOnQueryCompleteListener != null) {
            mOnQueryCompleteListener.onPreQueryComplete(token, cursor);
        }
        if (cookie != null) {
            CursorAdapter adapter = (CursorAdapter) cookie;
            adapter.changeCursor(cursor);
        }
        if (mOnQueryCompleteListener != null) {
            mOnQueryCompleteListener.onPostQueryComplete(token, cursor);
        }
    }

    public interface OnQueryCompleteListener {
        /**
         * 当adapter刷新数据之前, 回调
         */
        public void onPreQueryComplete(int token, Cursor cursor);

        /**
         * 当adapter刷新数据之后, 回调
         */
        public void onPostQueryComplete(int token, Cursor cursor);
    }
}
