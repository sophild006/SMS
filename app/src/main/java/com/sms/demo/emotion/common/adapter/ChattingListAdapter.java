package com.sms.demo.emotion.common.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sms.demo.R;
import com.sms.demo.contact.sms.bean.ReceiverMsg;
import com.sms.demo.contact.sms.util.SmsUtil;
import com.sms.demo.contact.sms.view.XCRoundImageView;
import com.sms.demo.emotion.Main;
import com.sms.demo.emotion.bean.ImMsgBean;
import com.sms.demo.emotion.common.SimpleCommonUtils;
import com.sms.demo.emotion.common.utils.ImageLoadUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sj.keyboard.utils.imageloader.ImageBase;

public class ChattingListAdapter extends BaseAdapter {

    private final int VIEW_TYPE_COUNT = 8;
    private final int VIEW_TYPE_RECEIVER = 0;
    private final int VIEW_TYPE_SEND = 1;

    private Activity mActivity;
    private LayoutInflater mInflater;
    private List<ReceiverMsg> mData;
    private ListView listView;
    private boolean isLongClick = false;

    public ChattingListAdapter(Activity activity, ListView listView, ArrayList<ReceiverMsg> list) {
        this.mActivity = activity;
        this.listView = listView;
        mInflater = LayoutInflater.from(activity);
        mData = list;
        if (mData != null && mData.size() > 0) {
            Collections.reverse(mData);
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) == null) {
            return -1;
        }
        int type = mData.get(position).getType();
        if (type == 1) {
            return VIEW_TYPE_RECEIVER;
        } else if (type == 2) {
            return VIEW_TYPE_SEND;
        } else {
            return -1;
        }
    }

//    @Override
//    public int getViewTypeCount() {
//        return VIEW_TYPE_COUNT;
//    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ReceiverMsg bean = mData.get(position);
        final ViewHolderLeftText holder;
        int type = getItemViewType(position);

        switch (type) {
            case VIEW_TYPE_RECEIVER:
                if (convertView == null) {
                    holder = new ViewHolderLeftText();
                    convertView = mInflater.inflate(R.layout.listitem_cha_left_text, null);
                    convertView.setFocusable(true);
                    holder.iv_avatar = (XCRoundImageView) convertView.findViewById(R.id.iv_avatar);
                    holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                    holder.iv_checkbox = (ImageView) convertView.findViewById(R.id.iv_checkbox);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderLeftText) convertView.getTag();
                }
                holder.tv_content.setText(mData.get(position).getBody() + "");
                byte[] byte_ = mData.get(position).getBitmap();
                if (byte_ != null && byte_.length > 0) {
                    Bitmap bitmap = SmsUtil.getBitmap(mData.get(position).getBitmap());
                    holder.iv_avatar.setImageBitmap(bitmap);
                }
                if (isLongClick) {
                    holder.iv_checkbox.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_checkbox.setVisibility(View.GONE);
                }
                if (mData.get(position).isSecleted()) {
                    holder.iv_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                }
                break;
            case VIEW_TYPE_SEND:
                if (convertView == null) {
                    holder = new ViewHolderLeftText();
                    convertView = mInflater.inflate(R.layout.listitem_cha_right_text, null);
                    convertView.setFocusable(true);
                    holder.iv_avatar = (XCRoundImageView) convertView.findViewById(R.id.iv_avatar);
                    holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                    holder.iv_checkbox = (ImageView) convertView.findViewById(R.id.iv_checkbox);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderLeftText) convertView.getTag();
                }
                byte[] byte_1 = mData.get(position).getBitmap();
                if (byte_1 != null && byte_1.length > 0) {
                    Bitmap bitmap = SmsUtil.getBitmap(mData.get(position).getBitmap());
                    holder.iv_avatar.setImageBitmap(bitmap);
                }
                holder.tv_content.setText(mData.get(position).getBody() + "");
                if (isLongClick) {
                    holder.iv_checkbox.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_checkbox.setVisibility(View.GONE);
                }
                if (mData.get(position).isSecleted()) {
                    holder.iv_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                }
                break;
            default:
                if (convertView == null) {
                    holder = new ViewHolderLeftText();
                    convertView = mInflater.inflate(R.layout.listitem_cha_right_text, null);
                    convertView.setFocusable(true);
                    holder.iv_avatar = (XCRoundImageView) convertView.findViewById(R.id.iv_avatar);
                    holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                    holder.iv_checkbox = (ImageView) convertView.findViewById(R.id.iv_checkbox);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolderLeftText) convertView.getTag();
                }
                byte[] byte_2 = mData.get(position).getBitmap();
                if (byte_2 != null && byte_2.length > 0) {
                    Bitmap bitmap = SmsUtil.getBitmap(mData.get(position).getBitmap());
                    holder.iv_avatar.setImageBitmap(bitmap);
                }
                holder.tv_content.setText(mData.get(position).getBody() + "");
                if (isLongClick) {
                    holder.iv_checkbox.setVisibility(View.VISIBLE);
                } else {
                    holder.iv_checkbox.setVisibility(View.GONE);
                }
                if (mData.get(position).isSecleted()) {
                    holder.iv_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                }
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHolderLeftText holderLeftText = (ViewHolderLeftText) v.getTag();
                if (mData.get(position).isSecleted()) {
                    mData.get(position).setSecleted(false);
                    holderLeftText.iv_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_nochecknor);
                } else {
                    holderLeftText.iv_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                    mData.get(position).setSecleted(true);
                }
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongClick = true;
                notifyDataSetChanged();
                mData.get(position).setSecleted(true);
                if (mListener != null) {
                    mListener.onItemLongClick();
                }
                return true;
            }
        });
        return convertView;
    }

    public void setContent(TextView tv_content, String content) {
        SimpleCommonUtils.spannableEmoticonFilter(tv_content, content);
    }

    public void notifySelected() {
        isLongClick = false;
        for (int m = 0; m < mData.size(); m++) {
            mData.get(m).setSecleted(false);
        }
        notifyDataSetChanged();
    }

//    public void disPlayLeftImageView(int position, View view, ViewHolderLeftImage holder, ReceiverMsg bean) {
//        try {
//            if (ImageBase.Scheme.FILE == ImageBase.Scheme.ofUri(bean.getImage())) {
//                String filePath = ImageBase.Scheme.FILE.crop(bean.getImage());
//                //TODOf
//            } else {
//                ImageLoadUtils.getInstance(mActivity).displayImage(bean.getImage(), holder.iv_image);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public final class ViewHolderLeftText {
        public XCRoundImageView iv_avatar;
        public TextView tv_content;
        public ImageView iv_checkbox;
    }


    public void addData(ReceiverMsg bean, boolean isNotifyDataSetChanged, boolean isFromHead) {
        if (bean == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (isFromHead) {
            mData.add(0, bean);
        } else {
            mData.add(bean);
        }

        if (isNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }

    public onLongItemClickListener mListener;

    public interface onLongItemClickListener {
        void onItemLongClick();
    }

    public void setOnItemLongClickListener(onLongItemClickListener listener) {
        this.mListener = listener;
    }
}