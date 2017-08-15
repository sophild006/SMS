package com.sms.demo.contact.sms.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.sms.demo.R;
import com.sms.demo.contact.sms.privatebox.main.PrivateChooseActivity;


public class ChooseDialog extends Dialog implements View.OnClickListener {
    private TextView tvConversation, tvContact, tvNewNumber;
    private Context context;

    public ChooseDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_add_private, null);
        tvConversation = (TextView) mView.findViewById(R.id.tv_conversation);
        tvContact = (TextView) mView.findViewById(R.id.tv_contact);
        tvNewNumber = (TextView) mView.findViewById(R.id.tv_new_phone);
        super.setContentView(mView);
        initEvent();

    }

    private void initEvent() {

        tvConversation.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_conversation:
                context.startActivity(new Intent(context, PrivateChooseActivity.class));
                break;
            case R.id.tv_contact:
                break;
            case R.id.tv_new_phone:
                break;
        }
        dismiss();
    }
}
