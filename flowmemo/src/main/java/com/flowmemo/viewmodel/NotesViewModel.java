package com.flowmemo.viewmodel;


import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import com.flowmemo.R;
import com.flowmemo.adapter.NotesAdapter;
import com.flowmemo.base.BaseAdapter;
import com.flowmemo.base.BaseViewModel;
import com.flowmemo.model.Notes;
import com.flowmemo.presenter.NotesPresenter;
import com.flowmemo.BR;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */


public class NotesViewModel extends BaseViewModel {

    private NotesAdapter mAdapter = new NotesAdapter(BR.notes, R.layout.item_notes, new NotesPresenter());

    private MutableLiveData<List<Notes>> mNotesList = mAdapter.DataList;


    public NotesViewModel() {
        getNotesData();
    }

    private void getNotesData() {
        for (int i = 0; i < 100; i++) {
            Notes notes = new Notes("Label", "Content", "Just Now");
            mNotesList.getValue().add(notes);
        }
    }

    @Override
    public BaseAdapter getAdapter() {
        return mAdapter;
    }
}
