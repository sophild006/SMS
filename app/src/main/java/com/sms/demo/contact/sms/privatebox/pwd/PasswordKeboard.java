package com.sms.demo.contact.sms.privatebox.pwd;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.inputmethodservice.Keyboard;

import com.sms.demo.R;

/**
 * Created by Administrator on 2017/8/10.
 */

public class PasswordKeboard extends Keyboard {
    static int Code;

    public PasswordKeboard(Context context, int i) {
        this(context, i, 0);
    }

    public PasswordKeboard(Context context, int i, int i2) {
        super(context, i, i2);
        Code = context.getResources().getDimensionPixelOffset(R.dimen.password_keyboard_spacebar_vertical_correction);
    }

    @Override
    protected Key createKeyFromXml(Resources res, Row parent, int x, int y, XmlResourceParser parser) {
        return new Key(res, parent, x, y, parser);
    }
}
