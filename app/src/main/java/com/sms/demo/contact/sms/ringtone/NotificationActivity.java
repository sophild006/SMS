package com.sms.demo.contact.sms.ringtone;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.sms.demo.R;
import com.sms.demo.contact.sms.ringtone.adapter.RingAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class NotificationActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private RingViewPager mAdapter;
    private List<View> list = new ArrayList<>();
    private RelativeLayout relativelayout0, relativelayout1;
    private ImageView ivLeft, ivRight;
    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_select_ringtone);
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.container_view);
        relativelayout0 = (RelativeLayout) findViewById(R.id.relativelayout0);
        relativelayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);
        ivLeft = (ImageView) findViewById(R.id.imageview0);
        ivRight = (ImageView) findViewById(R.id.imageview1);
//        View view = new View(this);
//        view.setBackgroundColor(Color.parseColor("#000000"));
//        list.add(view);
//        View view1 = new View(this);
//        view1.setBackgroundColor(Color.parseColor("#e3e3e3"));
//        list.add(view1);
//        mAdapter = new RingViewPager(list);
//        viewPager.setAdapter(mAdapter);
        listView = (ListView) findViewById(R.id.listview);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        ivLeft.setVisibility(View.VISIBLE);
//                        ivRight.setVisibility(View.INVISIBLE);
//                        break;
//                    case 1:
//                        ivRight.setVisibility(View.VISIBLE);
//                        ivLeft.setVisibility(View.INVISIBLE);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });


        int[] iArr = new int[]{R.id.mid};
        String[] strArr = new String[]{"title"};
        Cursor query = getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, "is_notification = 1 or is_ringtone = 1", null, "title ASC");
//        listView.setAdapter(new RingD);
        RingAdapter adapter = new RingAdapter(this, R.layout.layout_ringtone_item, query, strArr, iArr);
        listView.setAdapter(adapter);
    }
}
