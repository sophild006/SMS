package com.sms.demo.emotion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sj.emoji.EmojiBean;
import com.sms.demo.R;
import com.sms.demo.Util.Util;
import com.sms.demo.contact.sms.SendActivity;
import com.sms.demo.contact.sms.Sms;
import com.sms.demo.contact.sms.bean.ReceiverMsg;
import com.sms.demo.contact.sms.dialog.PopMenuDialog;
import com.sms.demo.contact.sms.send.SendListenerManager;
import com.sms.demo.contact.sms.util.CommonAsyncQuery;
import com.sms.demo.contact.sms.util.SmsUtil;
import com.sms.demo.contact.sms.view.XCRoundImageView;
import com.sms.demo.emotion.bean.ImMsgBean;
import com.sms.demo.emotion.common.Constants;
import com.sms.demo.emotion.common.SimpleCommonUtils;
import com.sms.demo.emotion.common.adapter.ChattingListAdapter;
import com.sms.demo.emotion.common.widget.SimpleAppsGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sj.keyboard.XhsEmoticonsKeyBoard;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

/**
 * Created by Administrator on 2017/8/3.
 */

public class Main extends AppCompatActivity implements FuncLayout.OnFuncKeyBoardListener, View.OnClickListener {
    private ListView lvChat;
    private XhsEmoticonsKeyBoard ekBar;
    //    private ChattingListAdapter chattingListAdapter;
    private LinearLayout llToast;
    private TextView tvDelete, tvCopy, tvForward, tvMore, tvTitle;
    private String phoneNumber;
    private ImageView ivPhone, ivMenu;
    private PopMenuDialog dialog;
    private boolean isLongClick = false;
    private ConversationDetailAdapter mAdapter;
    private final String[] SMS_PROJECTION = {
            "_id",
            "body",
            "type",
            "date"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.emotion_main);
        initView();
        Intent intent = getIntent();
        if (intent != null) {
            phoneNumber = intent.getStringExtra("address");
            int thread_id = intent.getIntExtra("thread_id", -1);
            tvTitle.setText(phoneNumber);
            prepareData(thread_id);
        }
    }

    private void prepareData(int thread_id) {
        CommonAsyncQuery asyncQuery = new CommonAsyncQuery(getContentResolver(), new CommonAsyncQuery.OnQueryCompleteListener() {
            @Override
            public void onPreQueryComplete(int token, Cursor cursor) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPostQueryComplete(int token, Cursor cursor) {
                lvChat.setSelection(lvChat.getCount());        // 默认显示在最底部
            }
        });
        String selection = "thread_id = ?";
        String[] selectionArgs = {String.valueOf(thread_id)};
        asyncQuery.startQuery(0, mAdapter, Sms.SMS_URI, SMS_PROJECTION, selection, selectionArgs, "date");
    }

    private void initView() {
        ekBar = (XhsEmoticonsKeyBoard) findViewById(R.id.ek_bar);
        lvChat = (ListView) findViewById(R.id.lv_chat);
        llToast = (LinearLayout) findViewById(R.id.ll_bottom_toast);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvCopy = (TextView) findViewById(R.id.tv_copy);
        tvForward = (TextView) findViewById(R.id.tv_forward);
        tvMore = (TextView) findViewById(R.id.tv_more);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        ivPhone = (ImageView) findViewById(R.id.iv_phone);
        ivMenu = (ImageView) findViewById(R.id.iv_menu);

        dialog = new PopMenuDialog(this);

        llToast.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        tvCopy.setOnClickListener(this);
        tvForward.setOnClickListener(this);
        tvMore.setOnClickListener(this);

        ivPhone.setOnClickListener(this);
        ivMenu.setOnClickListener(this);
        initEmoticonsKeyBoardBar();
        initListView();

    }

    private void initEmoticonsKeyBoardBar() {
        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));
        ekBar.addOnFuncKeyBoardListener(this);
        ekBar.addFuncView(new SimpleAppsGridView(this));

        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }
        });

//        ekBar.getEmoticonsToolBarView().addFixedToolItemView(false, R.mipmap.icon_add, null, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SimpleTranslucentChatActivity.this, "ADD", Toast.LENGTH_SHORT).show();
//            }
//        });
//        ekBar.getEmoticonsToolBarView().addToolItemView(R.mipmap.icon_setting, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SimpleTranslucentChatActivity.this, "SETTING", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {
                        OnSendImage(((EmoticonEntity) o).getIconUri());
                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index = ekBar.getEtChat().getSelectionStart();
                    Editable editable = ekBar.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (EmoticonsKeyboardUtils.isFullScreen(this)) {
            boolean isConsum = ekBar.dispatchKeyEventInFullScreen(event);
            return isConsum ? isConsum : super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (llToast.getVisibility() == View.VISIBLE) {
                llToast.setVisibility(View.GONE);
//                mAdapter.notifySelected();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initListView() {
        mAdapter = new ConversationDetailAdapter(this, null);
        lvChat.setAdapter(mAdapter);
//        lvChat.setOnItemClickListener(this);
        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        ekBar.reset();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        lvChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                int type = cursor.getInt(TYPE_COLUMN_INDEX);
                ConversationDetailViewHolder holder = (ConversationDetailViewHolder) view.getTag();
                if (holder != null) {
                    if (type == Sms.SEND_TYPE) {
                        holder.iv_send_checkbox.setVisibility(View.VISIBLE);
                        holder.iv_send_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                    } else if (type == Sms.RECEIVE_TYPE) {
                        holder.iv_receiver_checkbox.setVisibility(View.VISIBLE);
                        holder.iv_receiver_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                    } else {
                        holder.iv_send_checkbox.setVisibility(View.VISIBLE);
                        holder.iv_send_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                    }
                    isLongClick = true;
                    mAdapter.notifyDataSetChanged();
                    llToast.setVisibility(View.VISIBLE);
                    return true;
                }
                return false;
            }
        });

        lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                int type = cursor.getInt(TYPE_COLUMN_INDEX);
                ConversationDetailViewHolder holder = (ConversationDetailViewHolder) view.getTag();
                if (holder != null) {
                    if (type == Sms.SEND_TYPE) {
                        holder.iv_send_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                    } else if (type == Sms.RECEIVE_TYPE) {
                        holder.iv_receiver_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                    } else {
                        holder.iv_send_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                    }
                }
//                if (mData.get(position).isSecleted()) {
//                    mData.get(position).setSecleted(false);
//                    holderLeftText.iv.setImageResource(R.drawable.zerotheme_msgtypedialog_nochecknor);
//                } else {
//                    holderLeftText.iv_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
//                    mData.get(position).setSecleted(true);
//                }
                mAdapter.notifyDataSetChanged();
            }
        });


    }

    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ReceiverMsg bean = new ReceiverMsg();
            bean.setType(2);
            bean.setBody(msg);
//            chattingListAdapter.addData(bean, true, false);
            scrollToBottom();
            SmsUtil.sendMessage(Main.this, phoneNumber, msg);
            ArrayList<ReceiverMsg> mList = new ArrayList<>();
            for (int m = 0; m < 1; m++) {
                ReceiverMsg msg1 = new ReceiverMsg();
                msg1.setAddress(phoneNumber);
                msg1.setPerson(SmsUtil.getContactName(getContentResolver(), phoneNumber));
                msg1.setType(2);
                msg1.setDate(Util.generateDate(System.currentTimeMillis()));
                msg1.setBody(msg);
                msg1.setBitmap(SmsUtil.getBytes(SmsUtil.getContactIcon(getContentResolver(), phoneNumber)));
                msg1.setPerson(SmsUtil.getContactName(getContentResolver(), phoneNumber));
                mList.add(msg1);
            }
            SendListenerManager.getInstance().sendListener(phoneNumber, mList);
        }
    }

    private void OnSendImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            OnSendBtnClick("[img]" + image);
        }
    }

    private void scrollToBottom() {
        lvChat.requestLayout();
        lvChat.post(new Runnable() {
            @Override
            public void run() {
                lvChat.setSelection(lvChat.getBottom());
            }
        });
    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {
    }

    @Override
    protected void onPause() {
        super.onPause();
        ekBar.reset();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bottom_toast:
                break;
            case R.id.tv_delete:
                Log.d("wwq", "delete");
                break;
            case R.id.tv_copy:
                Log.d("wwq", "copy");
                break;
            case R.id.tv_forward:
                Log.d("wwq", "forward");
                break;
            case R.id.tv_more:
                Log.d("wwq", "more");
                break;
            case R.id.iv_menu:
                openOrCloseMenu();
                break;
            case R.id.iv_phone:
                break;
        }
    }

    /**
     * 打开联系人详情页或者添加页
     */
    public void openOrCloseMenu() {
        if (this.dialog == null) {
            this.dialog = new PopMenuDialog(this);
        }
        if (this.dialog == null) {
            return;
        }
        if (this.dialog.isShowing()) {
            this.dialog.dismiss();
            return;
        }
        String str = null;
        if (!TextUtils.isEmpty(phoneNumber)) {
            this.dialog.show(this.ivMenu.getRight(), 0, phoneNumber);
        }

    }

    private final int BODY_COLUMN_INDEX = 1;
    private final int TYPE_COLUMN_INDEX = 2;
    private final int DATE_COLUMN_INDEX = 3;

    class ConversationDetailAdapter extends CursorAdapter {
        private final int VIEW_TYPE_RECEIVER = 0;
        private final int VIEW_TYPE_SEND = 1;
        private ConversationDetailViewHolder mHolder;

        public ConversationDetailAdapter(Context context, Cursor c) {
            super(context, c);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            mHolder = (ConversationDetailViewHolder) view.getTag();
            String body = cursor.getString(BODY_COLUMN_INDEX);
            Log.d("wwq", "body: " + body);
            long date = cursor.getLong(DATE_COLUMN_INDEX);
            int type = cursor.getInt(TYPE_COLUMN_INDEX);
            String contactName = SmsUtil.getContactName(getContentResolver(), phoneNumber);
            String strDate = null;
            if (DateUtils.isToday(date)) {
                // 显示时间格式
                strDate = DateFormat.getTimeFormat(context).format(date);
            } else {
                // 显示日期格式
                strDate = DateFormat.getDateFormat(context).format(date);
            }
            Log.d("wwq", "type: " + type);
            if (type == Sms.RECEIVE_TYPE) {
                mHolder.rlSend.setVisibility(View.GONE);
                mHolder.rlReceiver.setVisibility(View.VISIBLE);
                mHolder.tv_receiver_content.setText(body);
                mHolder.iv_receiver_avatar.setImageResource(R.drawable.zerotheme_default_head);
                if (isLongClick) {
                    mHolder.iv_receiver_checkbox.setVisibility(View.VISIBLE);
                } else {
                    mHolder.iv_receiver_checkbox.setVisibility(View.GONE);
                }
            } else if (type == Sms.SEND_TYPE) {
                mHolder.rlReceiver.setVisibility(View.GONE);
                mHolder.rlSend.setVisibility(View.VISIBLE);
                mHolder.tv_send_content.setText(body);
                mHolder.iv_send_avatar.setImageResource(R.drawable.zerotheme_default_head);
                mHolder.ivSendFail.setVisibility(View.GONE);
                if (isLongClick) {
                    mHolder.iv_send_checkbox.setVisibility(View.VISIBLE);
                } else {
                    mHolder.iv_send_checkbox.setVisibility(View.GONE);
                }
            } else {
                mHolder.rlReceiver.setVisibility(View.GONE);
                mHolder.rlSend.setVisibility(View.VISIBLE);
                mHolder.tv_send_content.setText(body);
                mHolder.ivSendFail.setVisibility(View.VISIBLE);
                mHolder.iv_send_avatar.setImageResource(R.drawable.zerotheme_default_head);
                if (isLongClick) {
                    mHolder.iv_send_checkbox.setVisibility(View.VISIBLE);
                } else {
                    mHolder.iv_send_checkbox.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            mHolder = new ConversationDetailViewHolder();


            View view = LayoutInflater.from(context).inflate(R.layout.listitem_cha_left_text, null);

            mHolder.rlReceiver = (RelativeLayout) view.findViewById(R.id.rl_receiver);
            mHolder.iv_send_avatar = (XCRoundImageView) view.findViewById(R.id.iv_receiver_avatar);
            mHolder.tv_send_content = (TextView) view.findViewById(R.id.tv_receiver_content);
            mHolder.iv_send_checkbox = (ImageView) view.findViewById(R.id.iv_receiver_checkbox);

            mHolder.rlSend = (RelativeLayout) view.findViewById(R.id.rl_send);
            mHolder.iv_send_avatar = (XCRoundImageView) view.findViewById(R.id.iv_send_avatar);
            mHolder.tv_send_content = (TextView) view.findViewById(R.id.tv_send_content);
            mHolder.iv_send_checkbox = (ImageView) view.findViewById(R.id.iv_send_checkbox);
            mHolder.ivSendFail = (ImageView) view.findViewById(R.id.iv_send_failed);
            view.setTag(mHolder);
            return view;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return super.getView(position, convertView, parent);
        }

        /**
         * 当CursorAdapter的内容改变时回调
         */
        @Override
        protected void onContentChanged() {
            super.onContentChanged();        // 需要调用super类中的onContentChanged方法之后, 再操作listView
            lvChat.setSelection(lvChat.getCount());
        }

    }

    public class ConversationDetailViewHolder {
        public RelativeLayout rlReceiver;
        public XCRoundImageView iv_receiver_avatar;
        public TextView tv_receiver_content;
        public ImageView iv_receiver_checkbox;
        public ImageView ivReceiverFail;

        public RelativeLayout rlSend;
        public XCRoundImageView iv_send_avatar;
        public TextView tv_send_content;
        public ImageView iv_send_checkbox;
        public ImageView ivSendFail;
    }

}
