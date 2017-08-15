package com.sms.demo.contact.sms.privatebox.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sms.demo.R;
import com.sms.demo.Util.Util;
import com.sms.demo.contact.sms.privatebox.bean.ThreadIdMsg;
import com.sms.demo.contact.sms.util.SmsUtil;
import com.sms.demo.contact.sms.view.XCRoundImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class PrivateAdapter extends BaseAdapter {

    private List<ThreadIdMsg> mList;
    private Context context;
    private LayoutInflater inflater;


    public PrivateAdapter(Context context, List<ThreadIdMsg> msg) {
        this.context = context;
        this.mList = msg;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ThreadIdMsg threadIdMsg = mList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_sms_main_listview_item, parent, false);
            holder.ivIcon = (XCRoundImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvBody = (TextView) convertView.findViewById(R.id.tv_item_body);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_item_date);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_item_phone_or_nam);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String phoneNumber = mList.get(position).getAddress();
        Bitmap bitmap = SmsUtil.getContactIcon(context.getContentResolver(), threadIdMsg.getAddress());
        String name = SmsUtil.getContactName(context.getContentResolver(), phoneNumber);
        String body = mList.get(position).getBody();
        String date = mList.get(position).getDate();
        if (TextUtils.isEmpty(name)) {
            name = "";
        } else {
            name = SmsUtil.getContactName(context.getContentResolver(), phoneNumber);
        }
        if (bitmap != null) {
            holder.ivIcon.setImageBitmap(bitmap);
        } else {
            holder.ivIcon.setImageResource(R.drawable.zerotheme_default_head);
        }
        holder.tvName.setText(name);
        holder.tvDate.setText(Util.generateDate(Long.parseLong(date)));
        holder.tvBody.setText(body);
        return convertView;
    }

    public class ViewHolder {
        private XCRoundImageView ivIcon;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvBody;
        public ImageView ivCheck;

    }


}
