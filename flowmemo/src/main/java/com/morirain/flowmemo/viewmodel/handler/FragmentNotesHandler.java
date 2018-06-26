package com.morirain.flowmemo.viewmodel.handler;


import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.ui.fragment.NotesContentFragment;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/17
 */


public class FragmentNotesHandler extends BaseCommandHandler {

    private BaseFragment mFragment;

    private NotesContentFragment mFragmentNotesContent;

    public FragmentNotesHandler(BaseFragment fragment) {
        this.mFragment = fragment;
    }

    public void onNewNoteClick(View view) {
        if (mFragmentNotesContent == null) mFragmentNotesContent = new NotesContentFragment();
        pageJump(mFragment.getActivity(), mFragmentNotesContent);
    }

    public void onNewPhotoClick(View view) {
        LogUtils.d("dasdas");
    }

    public void onNewVoiceClick(View view) {
        LogUtils.d("dasdas");
    }
}
