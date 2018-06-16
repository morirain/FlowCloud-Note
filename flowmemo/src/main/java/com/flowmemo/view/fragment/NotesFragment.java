package com.flowmemo.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.flowmemo.R;
import com.flowmemo.base.BaseAdapter;
import com.flowmemo.base.BaseFragment;
import com.flowmemo.model.Notes;
import com.flowmemo.databinding.FragmentNotesBinding;
import com.flowmemo.viewmodel.NotesViewModel;

public class NotesFragment extends BaseFragment<FragmentNotesBinding, NotesViewModel> {

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        getBinding().notesRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void setViewModel() {
        getBinding().setViewModel(getViewModel());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes;
    }

    @Override
    protected Class<NotesViewModel> getViewModelClass() {
        return NotesViewModel.class;
    }
}
