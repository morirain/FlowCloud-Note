package com.morirain.flowmemo.utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/7/11
 */

public class LabelEditTextMonitor {

    public interface IsChange {
        void isChange(boolean isChange);
    }

    private Editable mEditable;
    //如果文本不一致的回调
    private IsChange mIsChangeListener;
    private boolean mChanged = false;
    private String mDefaultContent;

    public LabelEditTextMonitor(@NonNull EditText editText) {
        this.mEditable = editText.getText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public final void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public final void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public final void afterTextChanged(Editable s) {
                if (s != mEditable) {
                    mEditable = s;
                }
                LabelEditTextMonitor.this.onTextChanged(s);
            }
        });
    }

    public void setIsChangeListener(IsChange isChange) {
        mIsChangeListener = isChange;
    }

    private void onTextChanged(Editable s) {
        if (s == null) s = mEditable;
        if (mIsChangeListener != null && mDefaultContent != null) {
            boolean changeBefore = !mDefaultContent.contentEquals(s);
            if (changeBefore != mChanged) {
                mChanged = changeBefore;
                mIsChangeListener.isChange(changeBefore);
            }
        }
    }

    public final void setDefaultText(String text) {
        mEditable.replace(0, mEditable.length(), text);
        mDefaultContent = text;
    }

}
