package com.morirain.flowmemo.model;


import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/15
 */

@Entity
public class Folder {

    @Id
    long boxId;

    @Transient
    public MutableLiveData<String> folderName = new MutableLiveData<>();
    @Transient
    private MutableLiveData<List<Notes>> mNotesList = new MutableLiveData<>();
    @Transient
    private MutableLiveData<Boolean> mIsSelected = new MutableLiveData<>();

    public Folder(String folderName) {
        this.folderName.setValue(folderName);
    }

    public Folder(File folderDir) {
        this.folderName.setValue(folderDir.getName());
    }

    public MutableLiveData<List<Notes>> getNotesList() {
        if (mNotesList.getValue() == null) mNotesList.setValue(new ArrayList<>());
        return mNotesList;
    }

    @NonNull
    public List<Notes> getNotesListValue() {
        if (mNotesList.getValue() == null) mNotesList.setValue(new ArrayList<>());
        return mNotesList.getValue();
    }

    public MutableLiveData<Boolean> isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected.setValue(selected);
    }
}
