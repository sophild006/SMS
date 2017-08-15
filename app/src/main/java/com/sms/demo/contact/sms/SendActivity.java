package com.sms.demo.contact.sms;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sj.emoji.EmojiBean;
import com.sms.demo.R;
import com.sms.demo.RecipientsEditor;
import com.sms.demo.Util.Util;
import com.sms.demo.contact.sms.bean.ReceiverMsg;
import com.sms.demo.contact.sms.send.SendListenerManager;
import com.sms.demo.contact.sms.util.SmsUtil;
import com.sms.demo.emotion.Main;
import com.sms.demo.emotion.common.Constants;
import com.sms.demo.emotion.common.SimpleCommonUtils;
import com.sms.demo.emotion.common.adapter.ChattingListAdapter;
import com.sms.demo.emotion.common.widget.SimpleAppsGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sj.keyboard.XhsEmoticonsKeyBoard;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

/**
 * Created by Administrator on 2017/8/7.
 */

public class SendActivity extends AppCompatActivity implements FuncLayout.OnFuncKeyBoardListener, View.OnClickListener {
    private ListView lvChat;
    private XhsEmoticonsKeyBoard ekBar;
    private ChattingListAdapter chattingListAdapter;
    private ArrayList<ReceiverMsg> mData = new ArrayList<>();
    private RecipientsEditor recipientsEditor;
    private RelativeLayout rlAddView;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.layout_send);
        Intent intent = getIntent();
        if (intent != null) {
            mData = (ArrayList<ReceiverMsg>) intent.getSerializableExtra("details");
        }
        initView();
        initAddView();
    }

    private void initAddView() {
        recipientsEditor.setDropDownAnchor(recipientsEditor.getId());
        final String[] arr = {"aa", "aab", "aac", "10086", "10010", "张三", "王五", "李四"};
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        recipientsEditor.setAdapter(arrayAdapter);
        recipientsEditor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                Log.d("wwq", "" + arr[position] + "  position: " + position + "  tv.getText() : " + tv.getText());
                recipientsEditor.add(tv.getText() + "", tv.getText() + "", true);
            }
        });
        recipientsEditor.setOnRecipientChangeListener(new RecipientsEditor.onRecipentEditorListener() {
            @Override
            public void onEditorListener(RecipientsEditor recipientsEditor, boolean z) {
                if (z) {
                    Log.d("wwq", "setOnRecipientChangeListener  onEditorListener");
                    recipientsEditor.getTextView().setText("");
                }
            }
        });

        findViewById(R.id.add_contact_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 100);
            }
        });
    }

    private void initView() {
        ekBar = (XhsEmoticonsKeyBoard) findViewById(R.id.ek_bar);
        lvChat = (ListView) findViewById(R.id.lv_chat);
        recipientsEditor = (RecipientsEditor) findViewById(R.id.recipients_editor);
        rlAddView = (RelativeLayout) findViewById(R.id.rl_add_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlAddView.setOnClickListener(this);

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
        return super.onKeyDown(keyCode, event);
    }

    private void initListView() {
        chattingListAdapter = new ChattingListAdapter(this, lvChat, mData);
        lvChat.setAdapter(chattingListAdapter);
        chattingListAdapter.setOnItemLongClickListener(new ChattingListAdapter.onLongItemClickListener() {
            @Override
            public void onItemLongClick() {
            }
        });
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
                Toast.makeText(SendActivity.this, "potision: " + position, Toast.LENGTH_SHORT).show();
                ChattingListAdapter.ViewHolderLeftText holder = (ChattingListAdapter.ViewHolderLeftText) view.getTag();
                if (holder != null) {
                    holder.iv_checkbox.setVisibility(View.VISIBLE);
                    holder.iv_checkbox.setImageResource(R.drawable.zerotheme_msgtypedialog_checkednor);
                }
                return true;
            }
        });
    }

    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (!isRecipientsEditorVisible()) {
                this.recipientsEditor.commit(false);
                List<String> recipients = this.recipientsEditor.getRecipients();
                String invalidPhone = SmsUtil.getInvalidPhone(recipients, false);
                if (!TextUtils.isEmpty(invalidPhone)) {
                    String code = generateTitle((int) R.string.has_invalid_recipient, invalidPhone);
                    Log.d("wwq", "code: " + code);
                    new AlertDialog.Builder(this).setIcon(R.drawable.zerotheme_edit_top_add_contact).setTitle(code).
                            setMessage(R.string.invalid_recipient_message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                } else {
                    if (recipients != null && recipients.size() > 0) {
                        rlAddView.setVisibility(View.GONE);
                        StringBuffer sb = new StringBuffer();
                        for (String receiver : recipients) {
                            if (sb.length() != 0) {
                                sb.append(", ");
                            }
                            sb.append(receiver);
                        }
                        tvTitle.setText(sb.toString());
                        SmsUtil.sendMessage(SendActivity.this, recipients.get(0), msg);
                        ReceiverMsg bean = new ReceiverMsg();
                        bean.setType(2);
                        bean.setBody(msg);
                        chattingListAdapter.addData(bean, true, false);
                        scrollToBottom();
                        ArrayList<ReceiverMsg> mList = new ArrayList<>();
                        for (int m = 0; m < recipients.size(); m++) {
                            ReceiverMsg msg1 = new ReceiverMsg();
                            msg1.setAddress(recipients.get(m));
                            msg1.setType(2);
                            msg1.setDate(Util.generateDate(System.currentTimeMillis()));
                            msg1.setBody(msg);
//                            msg1.setBitmap(SmsUtil.getBytes(SmsUtil.getContactIcon(getContentResolver(), recipients.get(m))));
                            msg1.setPerson(SmsUtil.getContactName(getContentResolver(), recipients.get(m)));
                            mList.add(msg1);
                        }
                        ekBar.getEtChat().setText("");
                        SendListenerManager.getInstance().sendListener(sb.toString(), mList);
                    } else {
                        Toast.makeText(SendActivity.this, "please input phoneNumber", Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("wwq", "invalidPhone: " + invalidPhone);
            }
        }
    }


    private String generateTitle(int i, String str) {
        return getResources().getString(i, new Object[]{str});
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
            case R.id.rl_add_view:

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            String recipientsFromOutSide = intent.getStringExtra("recipientsFromOutSide");
            Log.d("wwq", "recipientsFromOutSide: " + recipientsFromOutSide);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Log.i("NewMessageUI", "onActivityResult: " + uri);
            int contactID = SmsUtil.getContactID(getContentResolver(), uri);
            if (contactID != -1) {
                String contactAddress = SmsUtil.getContactAddress(getContentResolver(), contactID);
//                Log.d("wwq", "thread_id: " + SmsUtil.getThread_id(getContentResolver(), contactAddress));
                String contactName = SmsUtil.getContactName(getContentResolver(), contactAddress);
                recipientsEditor.add(contactName, contactAddress, true);
            } else {
                Toast.makeText(this, "当前联系人尚未添加手机号", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recipientsEditor.clearText();
        recipientsEditor.removeAll();
    }

    public boolean isRecipientsEditorVisible() {
        return this.recipientsEditor != null && this.recipientsEditor.getVisibility() == View.GONE;
    }


}
