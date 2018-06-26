package com.morirain.flowmemo.viewmodel.handler;


import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.LogUtils;
import com.morirain.flowmemo.base.BaseApplication;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.ui.fragment.NotesContentFragment;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/17
 */


public class FragmentNotesContentHandler extends BaseCommandHandler {

    private NotesContentFragment mFragment;

    public FragmentNotesContentHandler(NotesContentFragment fragment) {
        mFragment = fragment;
    }

    public void onSaveClick(View view) {
        LogUtils.d("dasdas");
    }

    public void onRequestFocusClick(View view) {
        mFragment.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) BaseApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(mFragment.getBinding().etNotesContentMarkdown, 0);
        }
    }
}
