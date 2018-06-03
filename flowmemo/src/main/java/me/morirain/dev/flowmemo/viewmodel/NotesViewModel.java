package me.morirain.dev.flowmemo.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
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

    public MutableLiveData<List<Notes>> getNotesList() {
        if (mNotesList == null) mNotesList = new MutableLiveData<>();
        return mNotesList;
    }

    public void setNotesList(List<Notes> notesList) {
        mNotesList.setValue(notesList);
    }

    public NotesViewModel(BaseAdapter<Notes> notesAdapter) {
        mNotesAdapter = notesAdapter;
        mNotesList = new MutableLiveData<>();
        setNotesList(new ArrayList<>());
        getNotes();
    }

    private void getNotes() {
        MutableLiveData<String> label = new MutableLiveData<String>();
        label.setValue("hello");
        Notes notes = new Notes(label, label, label);
        mNotesList.getValue().add(notes);
        mNotesAdapter.refresh(mNotesList.getValue());
    }
}
