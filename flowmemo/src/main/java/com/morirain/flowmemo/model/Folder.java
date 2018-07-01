package com.morirain.flowmemo.model;


import android.arch.lifecycle.MutableLiveData;

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
    public MutableLiveData<File> folderDir = new MutableLiveData<>();
    @Transient
    private List<Notes> mNotesList = new ArrayList<>();
    @Transient
    private MutableLiveData<Boolean> mIsSelected = new MutableLiveData<>();

    public Folder(String folderName) {
        this.folderName.postValue(folderName);
        this.folderDir.postValue(null);
    }

    public Folder(File folderDir) {
        this.folderName.postValue(folderDir.getName());
        this.folderDir.postValue(folderDir);
    }

    public List<Notes> getNotesList() {
        return mNotesList;
    }

    public MutableLiveData<Boolean> isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected.postValue(selected);
    }
}
