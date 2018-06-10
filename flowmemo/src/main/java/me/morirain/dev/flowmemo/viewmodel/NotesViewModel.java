package me.morirain.dev.flowmemo.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.base.BaseViewModel;
import me.morirain.dev.flowmemo.model.Notes;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */


public class NotesViewModel extends BaseViewModel {

    private BaseAdapter<Notes> mNotesAdapter;

    private MutableLiveData<List<Notes>> mNotesList;

    private List<Notes> mList;

    public LiveData<List<Notes>> getNotesList() {
        if (mNotesList == null) {
            mNotesList = mNotesAdapter.getList();
            getNotesData();
        }
        return mNotesList;
    }
    @Override
    protected void init() {

    }

    public void setAdapter(BaseAdapter<Notes> notesAdapter) {
        mNotesAdapter = notesAdapter;
        mNotesList = notesAdapter.getList();
        mList = mNotesList.getValue();
        getNotesData();
    }
    private void getNotesData() {
        for (int i = 0; i < 100; i++) {
            Notes notes = new Notes("Label", "Content", "Just Now");
            mNotesList.getValue().add(notes);
        }
    }
}
