package com.morirain.flowmemo.ui.fragment;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.adapter.NotesAdapter;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.databinding.FragmentNotesBinding;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingletonFactory;
import com.morirain.flowmemo.viewmodel.NotesViewModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


public class NotesFragment extends BaseFragment<FragmentNotesBinding, NotesViewModel> {

    private NoteLibraryRepository mRepository = SingletonFactory.getInstance(NoteLibraryRepository.class);

    private NotesAdapter mAdapter = new NotesAdapter(this, R.layout.item_notes);

    @Override
    protected void init(Bundle savedInstanceState) {
        initRefresh();
        initRecyclerView();
        initEvent();
    }

    private void initRefresh() {
        RefreshLayout refreshLayout = getBinding().refreshLayout;
        refreshLayout.setOnRefreshListener(r -> {
            r.finishRefresh(500, mRepository.refreshData());//传入false表示刷新失败
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = getBinding().notesRecyclerView;

        mAdapter.setViewModel(getViewModel());

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void initEvent() {
        getViewModel().newNotesClickEvent.observe(this, aVoid ->
                        switchActivity(NotesContentParentFragment.getInstance(null))
        );
        getViewModel().notesClickEvent.observe(this, notes ->
                        switchActivity(NotesContentParentFragment.getInstance(notes))
        );

    }

    @Override
    protected void setArguments() {

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
