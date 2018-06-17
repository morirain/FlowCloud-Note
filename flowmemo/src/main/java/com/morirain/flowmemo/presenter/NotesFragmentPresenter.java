package com.morirain.flowmemo.presenter;


import android.view.View;

import com.morirain.flowmemo.base.BasePresenter;
import com.morirain.flowmemo.databinding.FragmentNotesBinding;
import com.morirain.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/17
 */


public class NotesFragmentPresenter extends BasePresenter<FragmentNotesBinding> {
    @Override
    public void setPresenter(FragmentNotesBinding bind) {
        bind.setPresenter(this);
    }

    public void onNewNoteClick(View view) {
        LogUtil.d("dasdas");
    }

    public void onNewPhotoClick(View view) {
        LogUtil.d("dasdas");
    }

    public void onNewVoiceClick(View view) {
        LogUtil.d("dasdas");
    }
}
