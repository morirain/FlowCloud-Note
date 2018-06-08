package me.morirain.dev.flowmemo.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import me.morirain.dev.flowmemo.R;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.base.BaseFragment;
import me.morirain.dev.flowmemo.bean.Notes;
import me.morirain.dev.flowmemo.databinding.FragmentNotesBinding;
import me.morirain.dev.flowmemo.utils.LogUtil;
import me.morirain.dev.flowmemo.viewmodel.NotesViewModel;

public class NotesFragment extends BaseFragment<FragmentNotesBinding> implements View.OnClickListener, BaseAdapter.OnItemClickListener {

    private BaseAdapter<Notes> mNotesAdapter;

    private NotesViewModel mNotesViewModel;

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        getBinding().notesRecyclerView.setLayoutManager(layoutManager);

        mNotesAdapter = new BaseAdapter<>(this, me.morirain.dev.flowmemo.BR.notes, R.layout.item_notes);
        mNotesAdapter.setOnItemClickListener(this);
        getBinding().notesRecyclerView.setAdapter(mNotesAdapter);
        getBinding().newNote.setOnClickListener(this);
        getBinding().newPhotoNote.setOnClickListener(this);
        getBinding().newVoiceNote.setOnClickListener(this);

    }

    private void initViewModel() {
        mNotesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        mNotesViewModel.init(mNotesAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == getBinding().newNote) {

        } else if (view == getBinding().newPhotoNote) {

        } else if (view == getBinding().newVoiceNote) {

        }
    }

    @Override
    public void onItemClick(View view, int position) {
        LogUtil.d("hello");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes;
    }


}
