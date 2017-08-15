package com.sms.demo;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sms.demo.contact.sms.util.SmsUtil;

import java.util.HashMap;

import static com.sms.demo.contact.sms.Config.ADDRESS_COLUMN_INDEX;
import static com.sms.demo.contact.sms.Config.BODY_COLUMN_INDEX;
import static com.sms.demo.contact.sms.Config.DATE_COLUMN_INDEX;

/**
 * Created by Administrator on 2017/8/4.
 */
public class ReceiverMsgAdapter extends CursorAdapter {
    private Context mContext;
    private HashMap<Integer, String> dateMap;
    private HashMap<Integer, Integer> smsRealPositionMap;
    private ReceiverHolder mHolder;

    public ReceiverMsgAdapter(Context context, Cursor c) {
        super(context, c);
        mContext = context;
        dateMap = new HashMap<>();
        smsRealPositionMap = new HashMap<>();
    }

    @Override
    protected void onContentChanged() {
        super.onContentChanged();
        Cursor cursor = getCursor();
        cursor.moveToPosition(-1);
        dateMap.clear();
        smsRealPositionMap.clear();
        prepareCursor(cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_sms_main_listview_item, null);
        mHolder = new ReceiverHolder();
        mHolder.ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
        mHolder.tvName = (TextView) view.findViewById(R.id.tv_item_phone_or_nam);
        mHolder.tvDate = (TextView) view.findViewById(R.id.tv_item_date);
        mHolder.tvBody = (TextView) view.findViewById(R.id.tv_item_body);
        view.setTag(mHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mHolder = (ReceiverHolder) view.getTag();
        String address = cursor.getString(ADDRESS_COLUMN_INDEX);
        String body = cursor.getString(BODY_COLUMN_INDEX);
        long date = cursor.getLong(DATE_COLUMN_INDEX);
        String contactName = SmsUtil.getContactName(mContext.getContentResolver(), address);
        if (TextUtils.isEmpty(contactName)) {
            mHolder.tvName.setText(address);
        } else {
            mHolder.tvName.setText(contactName);

            Bitmap contactIcon = SmsUtil.getContactIcon(mContext.getContentResolver(), address);
            if (contactIcon != null) {
                mHolder.ivIcon.setBackgroundDrawable(new BitmapDrawable(contactIcon));
            } else {
            }
        }

        String strDate = null;
        if (DateUtils.isToday(date)) {
            strDate = DateFormat.getTimeFormat(context).format(date);
        } else {
            strDate = DateFormat.getDateFormat(context).format(date);
        }
        mHolder.tvDate.setText(strDate);

        mHolder.tvBody.setText(body);

    }

    @Override
    public int getCount() {
        return dateMap.size() + smsRealPositionMap.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (dateMap.containsKey(position)) {        // 应该显示日期条目
            String date = dateMap.get(position);
            View tvDate = new TextView(mContext);
            return tvDate;
        }
        Cursor cursor = getCursor();
        cursor.moveToPosition(smsRealPositionMap.get(position));
        View v;
        if (convertView == null || convertView instanceof View) {
            v = newView(mContext, cursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, mContext, cursor);
        return v;
    }

    public class ReceiverHolder {
        public ImageView ivIcon;
        public TextView tvName;
        public TextView tvDate;
        public TextView tvBody;
    }

    public void prepareCursor(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            java.text.DateFormat dateFormat = DateFormat.getDateFormat(mContext);
            int listViewPosition = 0;
            String date;
            while (cursor.moveToNext()) {
                // 取短信的日期
                date = dateFormat.format(cursor.getLong(DATE_COLUMN_INDEX));
                // 判断当前短信的日期是否在dateMap中已经存在
                if (!dateMap.containsValue(date)) {
                    // 添加到dateMap中
                    dateMap.put(listViewPosition, date);
                    listViewPosition++;
                }
                // 把当前短信在Cursor中的索引存放在smsRealPositionMap中
                smsRealPositionMap.put(listViewPosition, cursor.getPosition());
                listViewPosition++;
            }
            cursor.moveToPosition(-1);
        }
    }
}
