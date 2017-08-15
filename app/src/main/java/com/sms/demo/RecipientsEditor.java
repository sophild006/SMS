package com.sms.demo;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.sms.demo.Util.UiUtil;
import com.sms.demo.contact.sms.util.SendContactManager;

import java.util.ArrayList;
import java.util.List;

/* compiled from: ZeroSms */
public class RecipientsEditor extends ViewGroup implements OnClickListener {
    private int f6867B = -1;
    private ArrayList contactList = new ArrayList();
    private AutoCompleteTextView autoCompleteTextView;
    private int f6869D;
    private C1326m f6870F;
    private boolean f6871I = true;
    private int f6872L;
    private onRecipentEditorListener onEditorListener;
    private boolean isActTextChanged;
    private int actRightPadding;
    private int actBottomPadding;
    private int actLeftMargin;
    private int actTopMargin;
    private int actRightMargin;
    private int actBottomMargin;
    private int actMinWidth;
    private int actHeight;
    private Runnable actClearRunnable = new ActClearRunnable();

    public RecipientsEditor(Context context) {
        super(context);
        init();
    }

    public RecipientsEditor(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        initValue();
        initView();
        initImageView();
        resetHint();
        this.autoCompleteTextView.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    commit(false);
                }
            }
        });
    }

    private void initView() {
        this.autoCompleteTextView = new AutoCompleteTextView(getContext());
        this.autoCompleteTextView.setEllipsize(TruncateAt.valueOf("END"));
        this.autoCompleteTextView.setBackgroundDrawable(null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, this.actHeight);
        params.setMargins(this.actLeftMargin, this.actTopMargin, this.actRightMargin, this.actBottomMargin);
        this.autoCompleteTextView.setPadding(this.f6869D, this.f6872L, this.actRightPadding, this.actBottomPadding);
        this.autoCompleteTextView.setTextSize(16.0f);
        this.autoCompleteTextView.setMinWidth(this.actMinWidth);
        this.autoCompleteTextView.setSingleLine();
        this.autoCompleteTextView.setGravity(16);
        this.autoCompleteTextView.setThreshold(1);
//        this.autoCompleteTextView.setDropDownBackgroundResource(R.drawable.recenttipbg);
        this.autoCompleteTextView.setDropDownAnchor(getId());
        this.autoCompleteTextView.setDropDownWidth(-1);
        this.autoCompleteTextView.setDropDownVerticalOffset(10);
        this.autoCompleteTextView.setDropDownHorizontalOffset(-90);
        addView(this.autoCompleteTextView, params);
        this.autoCompleteTextView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == 0 && keyCode == 67 && autoCompleteTextView.getText().length() == 0) {
                    if (f6867B > -1) {
                        remove(f6867B);
                        return true;
                    }
                    Code(getChildCount() - 3);
                    return true;
                } else if (keyEvent.getAction() != 0 || !m5595V(keyCode) || autoCompleteTextView.getText().length() == 0) {
                    return false;
                } else {
                    commit(true);
                    return true;
                }

            }
        });
        this.autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == 1) {
                    isActTextChanged = true;
                    requestLayout();
                } else if (charSequence.length() == 0) {
                    isActTextChanged = false;
                    requestLayout();
                }
                if (f6867B > -1) {
                    f6867B = -1;
                    Code(-1);
                }
                if (count == 1 && Code(charSequence.charAt(start))) {
                    commit(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        this.autoCompleteTextView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!f6871I) {
                    edit();
                }
                return false;

            }
        });
        this.autoCompleteTextView.setImeOptions(6);
        this.autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 6) {
                    return false;
                }
                commit(true);
                return true;

            }
        });
        int inputType = this.autoCompleteTextView.getInputType();
        if ((inputType & 15) == 1) {
            this.autoCompleteTextView.setRawInputType(inputType | 65536);
        }
        this.autoCompleteTextView.setTextColor(-1);
        this.autoCompleteTextView.setHintTextColor(-1);
        this.autoCompleteTextView.setFocusable(true);
    }

    private void initValue() {
        Context context = getContext();
        this.f6869D = UiUtil.dp2px(context, 0.0f);
        this.f6872L = UiUtil.dp2px(context, 5.0f);
        this.actRightPadding = UiUtil.dp2px(context, 0.0f);
        this.actBottomPadding = UiUtil.dp2px(context, 6.0f);
        this.actLeftMargin = UiUtil.dp2px(context, 3.0f);
        this.actTopMargin = UiUtil.dp2px(context, 5.0f);
        this.actRightMargin = UiUtil.dp2px(context, 3.0f);
        this.actBottomMargin = UiUtil.dp2px(context, 5.0f);
        this.actMinWidth = UiUtil.dp2px(context, 40.0f);
        this.actHeight = UiUtil.dp2px(context, 30.0f);
    }

    private ImageView f6874V;

    private void initImageView() {
        this.f6874V = new ImageView(getContext());
        this.f6874V.setVisibility(GONE);
        //TODO
        this.f6874V.setBackgroundResource(R.drawable.zerotheme_edit_top_add_contact);
        this.f6874V.setScaleType(ImageView.ScaleType.CENTER);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.actHeight, this.actHeight);
        layoutParams.setMargins(this.actLeftMargin, this.actTopMargin, this.actRightMargin, this.actBottomMargin);
        this.f6874V.setPadding(this.f6869D, this.f6872L, this.actRightPadding, this.actBottomPadding);
        addView(this.f6874V, layoutParams);
    }

    private boolean Code(String str) {
        if (str == null) {
            return false;
        }
        return this.contactList.contains(str);
    }

    private boolean m5597V(String str) {
        if (str == null || TextUtils.isEmpty(str)) {
            return false;
        }
        return true;
    }

    public boolean isEditing() {
        return this.f6871I;
    }

    public void setTextColor(int i) {
        this.autoCompleteTextView.setTextColor(i);
    }

    public void setHintTextColor(int i) {
        this.autoCompleteTextView.setHintTextColor(i);
    }

    public void commit(boolean z) {
        String trim = this.autoCompleteTextView.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            if (Code(trim.charAt(trim.length() - 1))) {
                trim = trim.substring(0, trim.length() - 1);
            }
            add(trim, trim, true);
        }
        if (z) {
            this.autoCompleteTextView.requestFocus();
        }
        m5587F();
        post(this.actClearRunnable);
    }

    public RecipientItemView add(String str, String str2, boolean z) {
        return add(str, str2, z, true);
    }

    public RecipientItemView add(String str, String str2, boolean z, boolean z2) {
        if (Code(str2) || !m5597V(str2)) {
            return null;
        }
        this.contactList.add(str2);
        if (TextUtils.isEmpty(str)) {
            str = str2;
        }
        SendContactManager.getmInstance().addContact(str2, str.equals(str2) ? "" : str, "");
        RecipientItemView recipientItemView = new RecipientItemView(getContext());
        recipientItemView.setText(str);
        recipientItemView.setTag(str2);
        recipientItemView.setOnClickListener(this);
        int childCount = getChildCount();
        recipientItemView.setChildIndex(childCount - 2);
        addView(recipientItemView, childCount - 2);
        resetHint();
        if (!z || this.onEditorListener == null) {
            return recipientItemView;
        }
        this.onEditorListener.onEditorListener(this, true);
        Log.d("wwq", "-----");
        return recipientItemView;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.autoCompleteTextView.setAdapter(adapter);
    }

    public ListAdapter getAdapter() {
        return this.autoCompleteTextView.getAdapter();
    }


    public List getRecipients() {
        return this.contactList;
    }

    public int getRecipientCount() {
        return this.contactList.size();
    }

    public boolean isEmpty() {
        return this.contactList.size() == 0 && this.autoCompleteTextView.getText().length() == 0;
    }

    public void clearText() {
        this.autoCompleteTextView.getEditableText().clear();
    }

    public void edit() {
        this.f6871I = true;
        commit(true);
        resetHint();
        requestLayout();
        this.autoCompleteTextView.requestFocus();
    }

    public void view() {
        this.f6871I = false;
        commit(false);
        Code(-1);
        resetHint();
        requestLayout();
        scrollTo(0, 0);
        this.autoCompleteTextView.clearFocus();
    }

    private void Code(int i) {
        this.f6867B = i;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount - 2; i2++) {
            boolean z;
            RecipientItemView recipientItemView = (RecipientItemView) getChildAt(i2);
            if (i2 == i) {
                z = true;
            } else {
                z = false;
            }
            recipientItemView.setItemSelected(z);
        }
    }

    public void removeAll() {
        SendContactManager.getmInstance().clear();
        if (this.contactList.size() != 0) {
            this.contactList.clear();
            this.f6867B = -1;
            removeViews(0, getChildCount() - 2);
            resetHint();
            if (this.onEditorListener != null) {
                this.onEditorListener.onEditorListener(this, false);
            }
        }
    }

    public void remove(int i) {
        SendContactManager.getmInstance().removeContact((String) this.contactList.remove(i));
        this.f6867B = -1;
        removeViewAt(i);
        resetChildIndex();
        resetHint();
        if (this.onEditorListener != null) {
            this.onEditorListener.onEditorListener(this, false);
        }
        if (this.f6870F != null) {
            this.f6870F.Code(this);
        }
    }

    private void resetChildIndex() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount - 2; i++) {
            ((RecipientItemView) getChildAt(i)).setChildIndex(i);
        }
    }

    private void resetHint() {
        int i = 0;
        int i2 = GONE;
        int childCount = getChildCount();
        if (childCount == 2) {
            this.autoCompleteTextView.setHint("Name or number");
        } else if (this.f6871I) {
            this.autoCompleteTextView.setHint(null);
        } else {
            this.autoCompleteTextView.setHint(m5591S());
        }
        if (this.f6871I) {
            i2 = GONE;
        }
        while (i < childCount - 2) {
//            getChildAt(i).setVisibility(i2);
            i++;
        }
    }

    private String m5591S() {
        int childCount = getChildCount();
        String string = getContext().getString(R.string.recipient_snap_text_suffix, new Object[]{Integer.valueOf(childCount - 2)});
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < childCount - 2; i++) {
            String obj = ((RecipientItemView) getChildAt(i)).getText().toString();
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(obj);
        }
        if (stringBuilder.length() > 24 || getRecipientCount() > 4) {
            stringBuilder.delete(stringBuilder.length() - 6, stringBuilder.length());
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    @Override
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = paddingTop;
        int i7 = paddingLeft;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != GONE) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (((layoutParams.leftMargin + i7) + measuredWidth) + layoutParams.rightMargin > i5 - paddingRight) {
                    i6 = paddingLeft;
                } else {
                    paddingTop = i6;
                    i6 = i7;
                }
                i6 += layoutParams.leftMargin;
                i7 = layoutParams.topMargin + paddingTop;
                measuredWidth += i6;
                childAt.layout(i6, i7, measuredWidth, measuredHeight + i7);
                i7 = measuredWidth + layoutParams.rightMargin;
                int bottom = layoutParams.bottomMargin + childAt.getBottom();
                i6 = paddingTop;
                paddingTop = bottom;
            }
        }
        if (this.isActTextChanged) {
            scrollTo(0, (this.autoCompleteTextView.getTop() - this.actTopMargin) - getPaddingTop());
        } else {
            scrollTo(0, 0);
        }
    }

    private int Code(int i, int i2) {
        return MeasureSpec.getSize(i);
    }

    private int m5592V(int i, int i2) {
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (MeasureSpec.getMode(i2) == MeasureSpec.EXACTLY) {
            return size2;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int childCount = getChildCount();
        int i3 = paddingTop + paddingBottom;
        Object obj = 1;
        size2 = 0;
        int i4 = 0;
        int i5 = paddingLeft;
        while (i4 < childCount - 1) {
            int i6;
            Object obj2;
            int i7;
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() == GONE) {
                i6 = size2;
                obj2 = obj;
                size2 = i5;
                i7 = i3;
            } else {
                int i8;
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                Object obj3 = null;
                int i9 = size - paddingRight;
                if (childAt instanceof AutoCompleteTextView) {
                    size2 = (((paddingTop + paddingBottom) + layoutParams.topMargin) + measuredHeight) + layoutParams.bottomMargin;
                    measuredWidth = this.actMinWidth;
                    obj3 = 1;
                }
                if (obj3 != null) {
                    i9 = (i9 - this.actLeftMargin) - this.actRightMargin;
                }
                if ((measuredWidth + (layoutParams.leftMargin + i5)) + layoutParams.rightMargin > i9) {
                    obj = 1;
                }
                if (obj != null) {
                    obj = null;
                    i5 = ((layoutParams.topMargin + i3) + measuredHeight) + layoutParams.bottomMargin;
                    i3 = paddingLeft;
                } else {
                    i8 = i5;
                    i5 = i3;
                    i3 = i8;
                }
                if (obj3 != null) {
                    layoutParams.width = ((i9 - i3) - layoutParams.leftMargin) - layoutParams.rightMargin;
                    childAt.setLayoutParams(layoutParams);
                    childAt.measure(MeasureSpec.makeMeasureSpec(layoutParams.width, MeasureSpec.EXACTLY), i2);
                }
                i6 = layoutParams.rightMargin + ((childAt.getMeasuredWidth() + i3) + layoutParams.leftMargin);
                obj2 = obj;
                i7 = i5;
                i8 = i6;
                i6 = size2;
                size2 = i8;
            }
            i4++;
            i3 = i7;
            obj = obj2;
            i5 = size2;
            size2 = i6;
        }
        if (this.isActTextChanged) {
            return size2;
        }
        return i3;
    }

    @Override
    protected void onMeasure(int i, int i2) {
        measureChildren(i, i2);
        setMeasuredDimension(Code(i, i2), m5592V(i, i2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.autoCompleteTextView.clearFocus();
        return super.onTouchEvent(motionEvent);
    }

    @Override
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    public void setOnRecipientChangeListener(onRecipentEditorListener c1084l) {
        this.onEditorListener = c1084l;
    }

    public void setOnRecipientStateChangeListener(C1326m c1326m) {
        this.f6870F = c1326m;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.autoCompleteTextView.addTextChangedListener(textWatcher);
    }

    public void removeTextChangedListener(TextWatcher textWatcher) {
        this.autoCompleteTextView.removeTextChangedListener(textWatcher);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.autoCompleteTextView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onClick(View view) {
        if (view instanceof RecipientItemView) {
            RecipientItemView recipientItemView = (RecipientItemView) view;
            if (recipientItemView.isItemSelected()) {
                remove(recipientItemView.getChildIndex());
            } else {
                Code(recipientItemView.getChildIndex());
            }
            this.autoCompleteTextView.requestFocus();
        }
    }

    private boolean Code(char c) {
        return c == ',' || c == ';';
    }

    private boolean m5595V(int i) {
        return i == 55 || i == 27 || i == 74 || i == 66;
    }

    private void m5587F() {
//        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
        //TODO
    }

    public void clearFocus() {
        super.clearFocus();
        this.autoCompleteTextView.clearFocus();
    }

    public TextView getTextView() {
        return this.autoCompleteTextView;
    }

    //TODO
    public void setDropDownAnchor(int i) {
        if (i != -1) {
            this.autoCompleteTextView.setDropDownAnchor(i);
            this.autoCompleteTextView.setDropDownVerticalOffset(0);
            return;
        }
        this.autoCompleteTextView.setDropDownAnchor(getId());
        this.autoCompleteTextView.setDropDownVerticalOffset(10);
    }

    public interface C1326m {
        void Code(RecipientsEditor recipientsEditor);
    }

    public interface onRecipentEditorListener {
        void onEditorListener(RecipientsEditor recipientsEditor, boolean z);
    }

    class ActClearRunnable implements Runnable {
        @Override
        public void run() {
            autoCompleteTextView.getEditableText().clear();
        }
    }

}