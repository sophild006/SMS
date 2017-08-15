package com.sms.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ScrollView;

import com.sms.demo.Util.UiUtil;

import static android.view.View.MeasureSpec.EXACTLY;

/* compiled from: ZeroSms */
public class ScrollviewSupportMaxHeight extends ScrollView {
    private int Code = 150;

    public ScrollviewSupportMaxHeight(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.Code = UiUtil.dp2px(context, 150.0f);
    }

    private int Code(int i) {
        int size = MeasureSpec.getSize(i);
        MeasureSpec.getMode(i);
        return size;
    }

    private int m5599V(int i) {
        int size = MeasureSpec.getSize(i);
        if (MeasureSpec.getMode(i) == EXACTLY) {
            return size;
        }
        if (getChildCount() == 1) {
            size = getChildAt(0).getMeasuredHeight();
        }
        if (size > this.Code) {
            return this.Code;
        }
        return size;
    }

    @Override
    protected void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        setMeasuredDimension(Code(i), m5599V(i2));
    }

    public void setMaxHeight(int i) {
        this.Code = i;
    }
}