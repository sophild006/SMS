package com.sms.demo.contact.sms.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;

import com.sms.demo.R;
import com.sms.demo.Util.GlobalContext;
import com.sms.demo.contact.sms.privatebox.main.PrivateListActivity;

public class PopMainMenuDialog extends Dialog implements OnClickListener {
    private int width;
    private int height;
    private Activity activity;
    private LinearLayout llPrivatebox;
    private View inflate;

    public PopMainMenuDialog(Activity activity) {
        super(activity, R.style.popupmenu);
        inflate = ((LayoutInflater) getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.main_popup_menu, null);
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
        llPrivatebox = (LinearLayout) inflate.findViewById(R.id.ll_private_box);
        llPrivatebox.setOnClickListener(this);
    }

    public void show(int i, int i2) {
        LayoutParams attributes = getWindow().getAttributes();
        if (this.activity.getResources().getConfiguration().orientation == 2) {
            attributes.x = Math.abs(this.width - this.height) + i;
        } else {
            attributes.x = i;
        }
        attributes.y = i2;
        getWindow().setAttributes(attributes);
        show();
    }

    @Override
    public void onClick(View view) {
        dismiss();
        int id = view.getId();
        switch (id) {
            case R.id.ll_private_box:
                activity.startActivity(new Intent(activity, PrivateListActivity.class));
                break;
        }

    }

}