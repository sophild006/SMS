package com.sms.demo.contact.sms.privatebox.pwd;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/* compiled from: ZeroSms */
public class PwdPageAdapter extends PagerAdapter {
    List list;

    public PwdPageAdapter(List list) {
        this.list = list;
    }

    public int getCount() {
        if (this.list != null) {
            return this.list.size();
        }
        return 0;
    }

    @Override
    public Object instantiateItem(View view, int i) {
        ((ViewPager) view).addView((View) this.list.get(i), 0);
        return this.list.get(i);
    }

    @Override
    public void destroyItem(View view, int i, Object obj) {
        ((ViewPager) view).removeView((View) this.list.get(i));
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

}