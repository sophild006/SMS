package com.sms.demo.contact.sms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sms.demo.R;
import com.sms.demo.Util.Util;
import com.sms.demo.contact.sms.bean.ReceiverMsg;
import com.sms.demo.contact.sms.util.SmsUtil;
import com.sms.demo.contact.sms.view.XCRoundImageView;
import com.sms.demo.emotion.Main;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.bitmap;
import static android.R.attr.fromAlpha;
import static android.R.attr.saveEnabled;

/**
 * Created by Administrator on 2017/8/4.
 */

public class MsgAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ReceiverMsg> mList;
    private LayoutInflater inflater;
    private Map<String, ArrayList<ReceiverMsg>> msgMap;
    private List<String> list_key = new ArrayList<>();

    public MsgAdapter(Context context, Map<String, ArrayList<ReceiverMsg>> list) {
        this.mContext = context;
        this.msgMap = list;
        mList = new ArrayList<>();
        inflater = LayoutInflater.from(mContext);
        for (String key : msgMap.keySet()) {
            list_key.add(key);
            Log.d("wwq", "key: " + key);
            mList.add(msgMap.get(key).get(0));
        }
        if (mList != null && mList.size() > 0) {
            Util.ListSort(mList);
        }
        Log.d("wwq", "listKey: " + list.size());
    }

    @Override
    public int getCount() {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        MsgHolder holder = null;
        Log.d("wwq", "getView: ");
        if (convertView == null) {
            holder = new MsgHolder();
            convertView = inflater.inflate(R.layout.layout_sms_main_listview_item, parent, false);
            holder.ivIcon = (XCRoundImageView) convertView.findViewById(R.id.iv_icon);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_item_phone_or_nam);
            holder.tvBody = (TextView) convertView.findViewById(R.id.tv_item_body);
            holder.tvDate = (TextView) convertView.findViewById(R.id.tv_item_date);
            convertView.setTag(holder);
        } else {
            holder = (MsgHolder) convertView.getTag();
        }
        holder.tvDate.setText(mList.get(position).getDate() + "");
        holder.tvBody.setText(mList.get(position).getBody());
        holder.tvName.setText(mList.get(position).getPerson());
//        String title = mList.get(position).getPerson();
//        if (TextUtils.isEmpty(title)) {
//            title = mList.get(position).getAddress();
//        }
//        Log.d("wwq", "title: " + title);
//        holder.tvName.setText(title + "");
//        byte[] byte_ = mList.get(position).getBitmap();
//        if (byte_ != null && byte_.length > 0) {
//            Bitmap bitmap = SmsUtil.getBitmap(byte_);
//            if (bitmap != null) {
//                holder.ivIcon.setImageBitmap(bitmap);
//            }
//        } else {
//            holder.ivIcon.setImageResource(R.drawable.zerotheme_default_head);
//        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("wwq", "phone: " + list_key.get(position) + "" +
                        "   \n" + "content: " + msgMap.get(list_key.get(position)).toString());
                Intent intent = new Intent(mContext, Main.class);
                ArrayList<ReceiverMsg> mm = msgMap.get(list_key.get(position));
                intent.putExtra("details", mm);
                intent.putExtra("phoneNumber", list_key.get(position));
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public void Refersh(String phoneNumber, ArrayList<ReceiverMsg> msg) {
        Log.d("wwq", "refersh.1.. " + msg.size());
        if (msgMap.keySet().contains(phoneNumber)) {
            for (ReceiverMsg receiverMsg : msg) {
                Log.d("wwq", "contains: " + receiverMsg.getAddress());
                msgMap.get(phoneNumber).add(receiverMsg);
            }
        } else {
            msgMap.put(phoneNumber, msg);
        }
        mList.clear();
        list_key.clear();
        if (msgMap != null && msgMap.size() > 0) {
            for (String key : msgMap.keySet()) {
                list_key.add(key);
                Util.ListSort(msgMap.get(key));
                Log.d("wwq", "key: " + key);
                mList.add(msgMap.get(key).get(0));
            }
        }
        Log.d("wwq", "mlist: " + mList.toString());
        if (mList != null && mList.size() > 0) {
            Util.ListSort(mList);
        }
        notifyDataSetChanged();
//        list_key.clear();
//        mList.clear();
//        for (String key : msgMap.keySet()) {
//            if (msgMap.keySet().contains(key)) {
//                msgMap.put(key, msgMap_.get(key));
//            } else {
//                msgMap.put(key, msgMap_.get(key));
//            }
//        }
//        Log.d("wwq", "refersh.2.. " + msgMap.size());
//        for (String key : this.msgMap.keySet()) {
//            list_key.add(key);
//            Log.d("wwq", "key: " + key);
//            mList.add(msgMap.get(key).get(0));
//        }

    }

    public class MsgHolder {
        private XCRoundImageView ivIcon;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvBody;
    }
}
