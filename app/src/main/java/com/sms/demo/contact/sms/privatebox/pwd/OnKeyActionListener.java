package com.sms.demo.contact.sms.privatebox.pwd;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Handler;
import android.util.Log;

import com.sms.demo.R;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/10.
 */

public class OnKeyActionListener implements KeyboardView.OnKeyboardActionListener {


    private static final HashMap values = new HashMap();
    private Handler handler;
    private Context context;
    private Keyboard keyboard;
    private KeyboardView keyboardView;
    private StringBuilder stringBuilder = new StringBuilder();

    static {
        values.put(Integer.valueOf(1), Integer.valueOf(0));
        values.put(Integer.valueOf(2), Integer.valueOf(1));
        values.put(Integer.valueOf(3), Integer.valueOf(2));
        values.put(Integer.valueOf(4), Integer.valueOf(3));
        values.put(Integer.valueOf(5), Integer.valueOf(4));
        values.put(Integer.valueOf(6), Integer.valueOf(5));
        values.put(Integer.valueOf(7), Integer.valueOf(6));
        values.put(Integer.valueOf(8), Integer.valueOf(7));
        values.put(Integer.valueOf(9), Integer.valueOf(8));
        values.put(Integer.valueOf(10), Integer.valueOf(9));
    }

    public OnKeyActionListener(Context context, KeyboardView keyboardView, Handler handler) {
        this.context = context;
        this.handler = handler;
        this.keyboardView = keyboardView;
        this.keyboard = new PasswordKeboard(this.context, R.xml.private_box_keyboard);
        this.keyboardView.setOnKeyboardActionListener(this);
        this.keyboardView.setKeyboard(this.keyboard);
        this.keyboardView.setPreviewEnabled(false);
    }


    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode >= 1 && primaryCode <= 10) {
            send(((Integer) values.get(Integer.valueOf(primaryCode))).intValue());
        } else if (primaryCode == 12 && this.stringBuilder.length() >= 1) {
            this.stringBuilder.deleteCharAt(this.stringBuilder.length() - 1);
            this.handler.sendEmptyMessage(4);
        }
        Log.d("wwq", "key:  " + primaryCode + "  iArr[]" + keyCodes.length);
    }

    private void send(int i) {
        if (this.stringBuilder.length() < 4) {
            this.stringBuilder.append(i);
            this.handler.sendEmptyMessage(1);
            if (this.stringBuilder.length() >= 4) {
                this.handler.sendEmptyMessageDelayed(2, 200);
            }
        }
    }

    public void resetBuffPwd() {
        if (this.stringBuilder == null) {
            this.stringBuilder = new StringBuilder();
        } else {
            this.stringBuilder.setLength(0);
        }
    }

    public String getBufferPwd() {
        return this.stringBuilder.toString();
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
