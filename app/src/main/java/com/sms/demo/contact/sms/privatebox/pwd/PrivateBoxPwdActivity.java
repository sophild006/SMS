package com.sms.demo.contact.sms.privatebox.pwd;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sms.demo.R;
import com.sms.demo.Util.PreferenceHelper;
import com.sms.demo.contact.sms.privatebox.PwdUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class PrivateBoxPwdActivity extends AppCompatActivity implements View.OnTouchListener {
    private List<View> mList;
    private int height;
    private MyHandler mHandler;
    private TextView tvTitle;
    private ImageView ivBack;
    private PasswordEntryKeyboardView passwordEntryKeyboardView;
    private ViewPager viewPager;
    private LayoutInflater inflater;
    private PwdPageAdapter mAdapter;
    private OnKeyActionListener keyActionListener;
    private TextView tvGuide1, tvGuide2;
    private ImageView[] ivList1, ivList2;
    private int currentItem = 0;
    private int pwdMode = 1;
    private String bufferPwd1, bufferPwd2;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    if (currentItem == 0) {
                        bufferPwd1 = keyActionListener.getBufferPwd();
                        changeView(bufferPwd1.length() - 1, 0);
                        return;
                    }
                    bufferPwd2 = keyActionListener.getBufferPwd();
                    changeView(bufferPwd2.length() - 1, 1);
                    break;
                case 2:
                    if (currentItem == 0) {
                        if (MODE_PWD == 1) {
                            keyActionListener.resetBuffPwd();
                            Code(1);
                        } else if (MODE_PWD == 2) {
                            if (bufferPwd1.equalsIgnoreCase(PreferenceHelper.getString(PrivateBoxPwdActivity.this, "pwd", ""))) {
                                Intent intent = new Intent();
                                intent.putExtra(Telephony.Carriers.PASSWORD, bufferPwd1);
                                setResult(-1, intent);//设置OnActivityResult 回调（-1为resultCode）
                                finish();
                                return;
                            } else {
                                Toast.makeText(PrivateBoxPwdActivity.this, "password error", Toast.LENGTH_SHORT).show();
                                keyActionListener.resetBuffPwd();
                                Code(0);
                            }
                        }
                        return;
                    } else if (bufferPwd2.equals(bufferPwd1)) {
                        Intent intent = new Intent();
                        intent.putExtra(Telephony.Carriers.PASSWORD, bufferPwd1);
                        Log.d("wwq", "pwd: " + bufferPwd1);
                        Toast.makeText(PrivateBoxPwdActivity.this, "Setting Success", Toast.LENGTH_SHORT).show();
                        setResult(-1, intent);//设置OnActivityResult 回调（-1为resultCode）
                        PreferenceHelper.setString(PrivateBoxPwdActivity.this, "pwd", bufferPwd2);
                        finish();
                        return;
                    } else {
                        keyActionListener.resetBuffPwd();
                        Code(0);
                        Toast.makeText(PrivateBoxPwdActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                        return;
                    }
                case 4:
                    if (currentItem == 0) {
                        bufferPwd1 = keyActionListener.getBufferPwd();
                        m4079V(bufferPwd1.length(), 0);
                        return;
                    }
                    bufferPwd2 = keyActionListener.getBufferPwd();
                    m4079V(bufferPwd2.length(), 1);
                    break;
            }
        }
    }

    private int MODE_PWD = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        LayoutInflater layoutInflater = getLayoutInflater();
        this.mList = new ArrayList();
        setContentView(R.layout.layout_private_pwd);
        Intent intent = getIntent();
        if(TextUtils.isEmpty(PreferenceHelper.getString(this,"pwd",""))){
            MODE_PWD=1;
        }else{
            if (intent != null) {
                MODE_PWD = intent.getIntExtra("mode_pwd", 1);
            }
        }
        initView();
        if (MODE_PWD == 1) {
            initData();
        } else if (MODE_PWD == 2) {

        }

    }


    private void initView() {
        mHandler = new MyHandler();
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        passwordEntryKeyboardView = (PasswordEntryKeyboardView) findViewById(R.id.keyboard);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        View inflate;
        View inflate2;
        inflater = LayoutInflater.from(this);
        if (height <= 480 || PwdUtil.isMiniScreen()) {
            inflate = inflater.inflate(R.layout.private_box_center_view_mini, null);
            inflate2 = inflater.inflate(R.layout.private_box_center_view_mini, null);
            this.mList.add(inflate);
            this.mList.add(inflate2);
        } else {
            inflate = inflater.inflate(R.layout.private_box_center_view, null);
            inflate2 = inflater.inflate(R.layout.private_box_center_view, null);
            this.mList.add(inflate);
            this.mList.add(inflate2);
        }
        getWindow().setFlags(131072, 131072);
        getWindow().setSoftInputMode(18);

        this.mAdapter = new PwdPageAdapter(this.mList);
        this.viewPager.setAdapter(this.mAdapter);
        this.viewPager.setOnTouchListener(this);
        this.viewPager.setAnimationCacheEnabled(true);
        this.passwordEntryKeyboardView = (PasswordEntryKeyboardView) findViewById(R.id.keyboard);
        this.keyActionListener = new OnKeyActionListener(this, this.passwordEntryKeyboardView, mHandler);
        this.tvGuide1 = (TextView) ((View) this.mList.get(0)).findViewById(R.id.guideText);
        this.tvGuide2 = (TextView) ((View) this.mList.get(1)).findViewById(R.id.guideText);
        this.ivList1 = new ImageView[4];
        this.ivList1[0] = (ImageView) ((View) this.mList.get(0)).findViewById(R.id.iv1);
        this.ivList1[1] = (ImageView) ((View) this.mList.get(0)).findViewById(R.id.iv2);
        this.ivList1[2] = (ImageView) ((View) this.mList.get(0)).findViewById(R.id.iv3);
        this.ivList1[3] = (ImageView) ((View) this.mList.get(0)).findViewById(R.id.iv4);
        this.ivList2 = new ImageView[4];
        this.ivList2[0] = (ImageView) ((View) this.mList.get(1)).findViewById(R.id.iv1);
        this.ivList2[1] = (ImageView) ((View) this.mList.get(1)).findViewById(R.id.iv2);
        this.ivList2[2] = (ImageView) ((View) this.mList.get(1)).findViewById(R.id.iv3);
        this.ivList2[3] = (ImageView) ((View) this.mList.get(1)).findViewById(R.id.iv4);
        initCurrentItem();
        this.ivList1[0].setImageResource(R.drawable.private_box_input_foucs);
        this.ivList2[0].setImageResource(R.drawable.private_box_input_foucs);
    }

    private void initCurrentItem() {
        if (MODE_PWD == 1) {
            if (this.pwdMode == 1) {
                this.currentItem = 0;
                this.viewPager.setCurrentItem(0);
                this.tvTitle.setText("Set Password");
                this.tvGuide1.setText("New password");
                this.tvGuide2.setText("Confirm new password");
            } else if (this.pwdMode == 2) {
                this.currentItem = 0;
                this.viewPager.setCurrentItem(0);
                this.tvTitle.setText("Change Password");
                this.tvGuide1.setText("New password");
                this.tvGuide2.setText("Confirm new password");
            }
        } else if (MODE_PWD == 2) {
            this.viewPager.setCurrentItem(0);
            this.tvTitle.setText("Private box");
            this.tvGuide1.setText("Enter Password");
        }
    }

    private void initData() {

    }


    private void Code(int i) {
        if (i == 0) {
            this.viewPager.setCurrentItem(0);
            this.currentItem = 0;
            changeView(-1, -1);
            this.bufferPwd1 = "";
            this.bufferPwd2 = "";
            return;
        }
        this.viewPager.setCurrentItem(1);
        this.currentItem = 1;
        changeView(-1, 1);
        this.bufferPwd2 = "";

    }

    private void changeView(int i, int i2) {
        int i3 = 1;
        if (i > 3) {
            i = 3;
        }
        if (i2 == 0) {
            if (i == -1) {
                this.ivList1[0].setImageResource(R.drawable.private_box_input_foucs);
                while (i3 < this.ivList1.length) {
                    this.ivList1[i3].setImageResource(R.drawable.private_box_input_empty);
                    i3++;
                }
                return;
            }
            this.ivList1[i].setImageResource(R.drawable.private_box_input_fill);
            if (i < 3) {
                this.ivList1[i + 1].setImageResource(R.drawable.private_box_input_foucs);
            }
        } else if (i2 == 1) {
            if (i == -1) {
                this.ivList2[0].setImageResource(R.drawable.private_box_input_foucs);
                while (i3 < this.ivList2.length) {
                    this.ivList2[i3].setImageResource(R.drawable.private_box_input_empty);
                    i3++;
                }
                return;
            }
            this.ivList2[i].setImageResource(R.drawable.private_box_input_fill);
            if (i < 3) {
                this.ivList2[i + 1].setImageResource(R.drawable.private_box_input_foucs);
            }
        } else if (i2 != -1) {
        } else {
            if (i == -1) {
                this.ivList1[0].setImageResource(R.drawable.private_box_input_foucs);
                this.ivList2[0].setImageResource(R.drawable.private_box_input_foucs);
                while (i3 < this.ivList2.length) {
                    this.ivList1[i3].setImageResource(R.drawable.private_box_input_empty);
                    this.ivList2[i3].setImageResource(R.drawable.private_box_input_empty);
                    i3++;
                }
                return;
            }
            this.ivList1[i].setImageResource(R.drawable.private_box_input_fill);
            this.ivList2[i].setImageResource(R.drawable.private_box_input_fill);
            if (i < 3) {
                this.ivList1[i + 1].setImageResource(R.drawable.private_box_input_foucs);
                this.ivList2[i + 1].setImageResource(R.drawable.private_box_input_foucs);
            }
        }

    }

    private void m4079V(int i, int i2) {
        if (i > 3) {
            i = 3;
        }
        if (i2 == 0) {
            if (i < 3) {
                this.ivList1[i + 1].setImageResource(R.drawable.private_box_input_empty);
            }
            this.ivList1[i].setImageResource(R.drawable.private_box_input_foucs);
        } else if (i2 == 1) {
            if (i < 3) {
                this.ivList2[i + 1].setImageResource(R.drawable.private_box_input_empty);
            }
            this.ivList2[i].setImageResource(R.drawable.private_box_input_foucs);
        } else if (i2 == -1) {
            if (i < 3) {
                this.ivList1[i + 1].setImageResource(R.drawable.private_box_input_empty);
                this.ivList2[i + 1].setImageResource(R.drawable.private_box_input_empty);
            }
            this.ivList1[i].setImageResource(R.drawable.private_box_input_foucs);
            this.ivList2[i].setImageResource(R.drawable.private_box_input_foucs);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
