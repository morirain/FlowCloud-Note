package com.morirain.flowmemo.model;


import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.utils.ApplicationConfig;

import java.io.File;
import java.util.Date;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class Notes{

    public MutableLiveData<String> noteLabel = new MutableLiveData<>();

    public MutableLiveData<String> noteContent = new MutableLiveData<>();

    private MutableLiveData<String> mNoteLastUpdateTime = new MutableLiveData<>();

    public MutableLiveData<String> notePreview = new MutableLiveData<>();

    private Date mNoteLastUpdateDate;

    public File notePath;

    private String noteFolder = null;

    public Notes(String noteLabel, String noteContent, Date noteLastUpdateDate, String notePreview, File notePath) {
        this.noteLabel.setValue(noteLabel);
        this.noteContent.setValue(noteContent);
        this.notePreview.setValue(notePreview);
        this.mNoteLastUpdateDate = (noteLastUpdateDate);
        this.notePath = notePath;
    }

    // modify
    public Notes(Notes note) {
        this.noteLabel.setValue(note.noteLabel.getValue());
        this.noteContent.setValue(note.noteContent.getValue());
        this.notePreview.setValue(note.notePreview.getValue());
        this.mNoteLastUpdateDate = (note.mNoteLastUpdateDate);
        this.notePath = note.notePath;

    }
    // new
    public Notes(String folderName) {
        this.noteFolder = folderName;
        this.noteLabel.setValue(ApplicationConfig.NEW_NOTE_DEFALUT_LABEL);
    }

    public String getNoteParentDirName() {
        if (noteFolder != null) return noteFolder;
        return notePath.getParentFile().getName();
    }

    public Date getNoteLastUpdateDate() {
        return mNoteLastUpdateDate;
    }

    public void setNoteLastUpdateDate(Date date) {
        mNoteLastUpdateDate = date;
    }

    public MutableLiveData<String> getNoteLastUpdateTime() {
        mNoteLastUpdateTime.setValue(ApplicationConfig.PRETTY_TIME.format(mNoteLastUpdateDate));
        return mNoteLastUpdateTime;
    }

    public void refreshDate() {
        mNoteLastUpdateTime.setValue(ApplicationConfig.PRETTY_TIME.format(mNoteLastUpdateDate));
    }
}
