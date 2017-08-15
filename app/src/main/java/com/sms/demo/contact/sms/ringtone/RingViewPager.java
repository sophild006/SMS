package com.sms.demo.contact.sms.ringtone;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/* compiled from: ZeroSms */
public class RingViewPager extends PagerAdapter {
    List Code;

    public RingViewPager(List list) {
        this.Code = list;
    }

    @Override
    public int getCount() {
        if (this.Code != null) {
            return this.Code.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int i) {
        View view1 = (View) Code.get(i);
        if (view1 != null) {
            view.addView(view1);
        }
        return view1;
    }

    @Override
    public void destroyItem(ViewGroup view, int i, Object obj) {
        view.removeView((View) this.Code.get(i));
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
}