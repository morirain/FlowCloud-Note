package com.morirain.flowmemo.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.adapter.NotesAdapter;
import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.databinding.FragmentNotesBinding;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.viewmodel.NotesViewModel;
import com.morirain.flowmemo.viewmodel.handler.FragmentNotesHandler;


public class NotesFragment extends BaseFragment<FragmentNotesBinding, NotesViewModel> {

    private NotesAdapter mAdapter = new NotesAdapter(this, R.layout.item_notes);

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getBinding().notesRecyclerView;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        getViewModel().setAdapter(mAdapter);
    }

    @Override
    protected void setCustomViewModelConnect() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes;
    }

    @Override
    protected Class<NotesViewModel> getViewModelClass() {
        return NotesViewModel.class;
    }

    @Override
    protected BaseCommandHandler getHandler() {
        return new FragmentNotesHandler(this);
    }
}
