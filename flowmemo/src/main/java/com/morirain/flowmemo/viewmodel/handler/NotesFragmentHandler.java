package com.morirain.flowmemo.viewmodel.handler;


import android.view.View;

import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.FragmentNotesBinding;
import com.morirain.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/17
 */


public class NotesFragmentHandler extends BaseCommandHandler<FragmentNotesBinding> {

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
