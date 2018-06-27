package com.morirain.flowmemo.ui.fragment;

import android.os.Bundle;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.databinding.FragmentNotesContentPreviewBinding;
import com.morirain.flowmemo.viewmodel.NotesContentViewModel;

public class NotesContentPreviewFragment extends BaseFragment<FragmentNotesContentPreviewBinding, NotesContentViewModel> {

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @Override
    protected void setCustomViewModelConnect() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes_content_preview;
    }

    @Override
    protected Class<NotesContentViewModel> getViewModelClass() {
        return NotesContentViewModel.class;
    }

    @Override
    protected BaseCommandHandler getHandler() {
        return null;
    }
}
