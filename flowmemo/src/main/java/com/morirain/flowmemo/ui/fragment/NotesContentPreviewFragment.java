package com.morirain.flowmemo.ui.fragment;

import android.os.Bundle;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.viewmodel.NotesContentPreviewViewModel;
import com.morirain.flowmemo.databinding.FragmentNotesContentPreviewBinding;

public class NotesContentPreviewFragment extends BaseFragment<FragmentNotesContentPreviewBinding, NotesContentPreviewViewModel> {

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
    protected Class<NotesContentPreviewViewModel> getViewModelClass() {
        return NotesContentPreviewViewModel.class;
    }

    @Override
    protected BaseCommandHandler getHandler() {
        return null;
    }
}
