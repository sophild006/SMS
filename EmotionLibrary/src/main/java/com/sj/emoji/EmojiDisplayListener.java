package com.sj.emoji;

import android.content.Context;
import android.text.Spannable;

public abstract interface EmojiDisplayListener
{
  public abstract void onEmojiDisplay(Context paramContext, Spannable paramSpannable, String paramString, int paramInt1, int paramInt2, int paramInt3);
}