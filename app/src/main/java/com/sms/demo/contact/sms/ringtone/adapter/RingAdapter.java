package com.sms.demo.contact.sms.ringtone.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;

import com.sms.demo.Util.GlobalContext;

/* compiled from: ZeroSms */
public class RingAdapter extends SimpleCursorAdapter {
    SharedPreferences Code = PreferenceManager.getDefaultSharedPreferences(GlobalContext.getAppContext());

    public RingAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr) {
        super(context, i, cursor, strArr, iArr);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        Code(view, cursor);
    }

    private void Code(View view, Cursor cursor) {
        String string = cursor.getString(cursor.getColumnIndex("title"));
        String string2 = cursor.getString(cursor.getColumnIndex("_data"));
        long j = cursor.getLong(cursor.getColumnIndex("_id"));
        Uri withAppendedPath = Uri.withAppendedPath(Media.INTERNAL_CONTENT_URI, "" + j);
        Log.d("wwq", "string: " + string);
        Log.d("wwq", "string2: " + string2);
        Log.d("wwq", "withAppendedPath: " + withAppendedPath);

    }
}