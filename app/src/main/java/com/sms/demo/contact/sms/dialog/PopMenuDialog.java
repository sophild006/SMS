package com.sms.demo.contact.sms.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import com.sms.demo.R;
import com.sms.demo.Util.GlobalContext;
import com.sms.demo.contact.sms.contact.pin.PinActivity;

public class PopMenuDialog extends Dialog implements OnClickListener {
    private int width;
    private int height;
    private Activity activity;
    private LinearLayout llContact;
    private LinearLayout llNotification;
    private LinearLayout llBlock;
    private View inflate;
    private String phoneNumber;

    public PopMenuDialog(Activity activity) {
        super(activity, R.style.popupmenu);
        inflate = ((LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.compose_popup_menu, null);
        this.activity = activity;
        setContentView(inflate);
        init();
        WindowManager windowManager = (WindowManager) GlobalContext.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.width = displayMetrics.widthPixels;
        this.height = displayMetrics.heightPixels;
    }

    private void init() {

        setCanceledOnTouchOutside(true);
        LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 51;
        attributes.width = -2;
        attributes.height = -2;
        attributes.format = -2;
        attributes.alpha = 1.0f;
        attributes.dimAmount = 0.5f;
        getWindow().setAttributes(attributes);

        llContact = (LinearLayout) inflate.findViewById(R.id.ll_contact);
        llNotification = (LinearLayout) inflate.findViewById(R.id.ll_notification);
        llBlock = (LinearLayout) inflate.findViewById(R.id.ll_add_block);
        llContact.setOnClickListener(this);
        llNotification.setOnClickListener(this);
        llBlock.setOnClickListener(this);
    }

    public void show(int i, int i2, String str) {
        LayoutParams attributes = getWindow().getAttributes();
        if (this.activity.getResources().getConfiguration().orientation == 2) {
            attributes.x = Math.abs(this.width - this.height) + i;
        } else {
            attributes.x = i;
        }
        attributes.y = i2;
        getWindow().setAttributes(attributes);
        phoneNumber = str;
        show();
    }

    @Override
    public void onClick(View view) {
        dismiss();
        int id = view.getId();
        switch (id) {
            case R.id.ll_contact:
                openContactDetails();
                break;
            case R.id.ll_notification:
                break;
            case R.id.ll_add_block:
                activity.startActivity(new Intent(activity, PinActivity.class));

                break;

        }

    }

    /**
     * 打开联系人详情页
     */
    private void openContactDetails() {
        Uri uriNumber2Contacts = Uri.parse("content://com.android.contacts/"
                + "data/phones/filter/" + phoneNumber);
        Cursor cursorCantacts = activity.getContentResolver().query(
                uriNumber2Contacts,
                null,
                null,
                null,
                null);
        if (cursorCantacts.getCount() > 0) {
            cursorCantacts.moveToFirst();
            Long contactID = cursorCantacts.getLong(cursorCantacts.getColumnIndex("contact_id"));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(contactID));
            intent.setData(uri);
            activity.startActivity(intent);
        } else {
            openAddContact();
        }
    }

    private void openAddContact() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.dir/person");
        intent.setType("vnd.android.cursor.dir/contact");
        intent.setType("vnd.android.cursor.dir/raw_contact");
        // 添加姓名
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, phoneNumber);
        activity.startActivity(intent);
    }
}