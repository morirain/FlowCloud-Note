package com.flowmemo.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.flowmemo.R;
import com.flowmemo.base.BaseFragment;
import com.flowmemo.databinding.FragmentNotesBinding;
import com.flowmemo.presenter.NotesFragmentPresenter;
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
    protected void setConnect() {
        getBinding().setViewModel(getViewModel());
        getBinding().setPresenter(new NotesFragmentPresenter());
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
