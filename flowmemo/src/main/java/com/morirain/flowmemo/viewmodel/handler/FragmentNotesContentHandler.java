package com.morirain.flowmemo.viewmodel.handler;


import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.view.fragment.NotesContentFragment;

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
    }
}
