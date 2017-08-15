package com.sms.demo.contact.sms.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sms.demo.R;

public class ThemeTopPanel extends LinearLayout {
    private Context Code;
    private ImageView f6185I;
    private TextView f6186V;

    public ThemeTopPanel(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Code(context, attributeSet, i);
    }

    public ThemeTopPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Code(context, attributeSet, -1);
    }

    public ThemeTopPanel(Context context) {
        super(context);
        Code(context, null, -1);
    }

    private void Code(Context context, AttributeSet attributeSet, int i) {
        this.Code = context;
        LayoutInflater.from(context).inflate(R.layout.top_panel_layout, this, true);
        setBackgroundResource(R.drawable.zerotheme_top_panel);
        Code(attributeSet);
        Code();
    }

    private void Code(AttributeSet attributeSet) {
        this.f6186V = (TextView) findViewById(R.id.top_panel_title_name);
        this.f6186V.setTextColor(Color.parseColor("#fff"));
    }

    private void Code() {
        this.f6185I = (ImageView) findViewById(R.id.top_panel_back_view);
        this.f6185I.setImageResource(R.drawable.zerotheme_top_back_icon);
        this.f6185I.setImageResource(R.drawable.zerotheme_icon_background_selector);
    }

    public void setTitle(String str) {
        if (this.f6186V != null) {
            this.f6186V.setText(str);
        }
    }
}