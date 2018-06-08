package me.morirain.dev.flowmemo.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.bean.Notes;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */


public class NotesViewModel extends ViewModel {

    private BaseAdapter<Notes> mNotesAdapter;

    private MutableLiveData<List<Notes>> mNotesList;

    private List<Notes> mList;

    public LiveData<List<Notes>> getNotesList() {
        if (mNotesList == null) mNotesList = mNotesAdapter.getList();
        return mNotesList;
    }

    public void init(BaseAdapter<Notes> notesAdapter) {
        mNotesAdapter = notesAdapter;
        mNotesList = notesAdapter.getList();
        mList = mNotesList.getValue();
        getNotes();
    }
    private void getNotes() {
        MutableLiveData<String> label = new MutableLiveData<String>();
        label.setValue("hello");
        Notes notes = new Notes("asdsa", "fsad", "afsdew");
        mNotesList.getValue().add(notes);

        label = new MutableLiveData<String>();
        label.setValue("safdfdsasdaf");
        Notes n2 = new Notes("asdf", "gs", "fsd");
        mList.add(n2);
    }
}