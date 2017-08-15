package com.sms.demo;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sms.demo.Util.UiUtil;
import com.sms.demo.contact.sms.util.SmsUtil;

/* compiled from: ZeroSms */
public class RecipientItemView extends LinearLayout {
    private int f6854B;
    private int f6855C;
    private boolean Code;
    private int f6856D;
    private int f6857F;
    private int f6858I;
    private int f6859L;
    private int f6860S;
    private int f6861V;
    private int f6862Z;
    private int f6863a;
    private int f6864b;
    private C1319e f6865c = C1319e.Normal;
    private TextView f6866d;

    public RecipientItemView(Context context) {
        super(context);
        Code();
    }

    public RecipientItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Code();
    }

    private void Code() {
        m5583V();
        this.f6866d = new TextView(getContext());
        addView(this.f6866d);
        this.f6866d.setTextSize(16.0f);
        this.f6866d.setSingleLine();
        this.f6866d.setEllipsize(TruncateAt.END);
        m5582I();
    }

    private void m5583V() {
        this.f6858I = UiUtil.dp2px(getContext(), 10.0f);
        this.f6862Z = UiUtil.dp2px(getContext(), 0.0f);
        this.f6854B = UiUtil.dp2px(getContext(), 10.0f);
        this.f6855C = UiUtil.dp2px(getContext(), 0.0f);
        this.f6860S = UiUtil.dp2px(getContext(), 5.0f);
        this.f6857F = UiUtil.dp2px(getContext(), 0.0f);
        this.f6856D = UiUtil.dp2px(getContext(), 5.0f);
        this.f6859L = UiUtil.dp2px(getContext(), 0.0f);
        this.f6863a = UiUtil.dp2px(getContext(), 80.0f);
        this.f6864b = UiUtil.dp2px(getContext(), 42.0f);
    }

    private void m5582I() {
        if (this.Code) {
            setBackgroundResource(R.drawable.zerotheme_recipient_view_bg_selected);
            this.f6866d.setTextColor(-1);
        } else {
            setBackgroundResource(R.drawable.zerotheme_recipient_view_bg);
            this.f6866d.setTextColor(-1);
        }
//        switch (C1318d.Code[this.f6865c.ordinal()]) {
//            case 1:
//                this.f6866d.setTextColor(-1);
//                break;
//            default:
//                this.f6866d.setTextColor(-1);
//                break;
//        }
        m5584Z();
    }

    private void m5584Z() {
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, this.f6864b);
        layoutParams.setMargins(this.f6860S, this.f6857F, this.f6856D, this.f6859L);
        setPadding(this.f6858I, this.f6862Z, this.f6854B, this.f6855C);
        setLayoutParams(layoutParams);
        setGravity(17);
        this.f6866d.setMaxWidth(this.f6863a);
    }

    public int getChildIndex() {
        return this.f6861V;
    }

    public void setChildIndex(int i) {
        this.f6861V = i;
    }

    public void setText(String str) {
        this.f6866d.setText(str);
    }

    public CharSequence getText() {
        return this.f6866d.getText();
    }

    public boolean isItemSelected() {
        return this.Code;
    }

    public void setItemSelected(boolean z) {
        this.Code = z;
        m5582I();
    }

    public void setItemState(C1319e c1319e) {
        this.f6865c = c1319e;
        m5582I();
    }

    public enum C1319e {
        Normal,
        Loading,
        Online
    }
}