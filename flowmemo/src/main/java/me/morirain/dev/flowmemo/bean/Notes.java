package me.morirain.dev.flowmemo.bean;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;

import me.morirain.dev.flowmemo.BR;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class Notes{

    private MutableLiveData<String> noteLabel = new MutableLiveData<>();
    private MutableLiveData<String> noteContent = new MutableLiveData<>();
    private MutableLiveData<String> noteLastUpdateTime = new MutableLiveData<>();

    public Notes(String noteLabel, String noteContent, String noteLastUpdateTime) {
        this.noteLabel.setValue(noteLabel);
        this.noteContent.setValue(noteContent);
        this.noteLastUpdateTime.setValue(noteLastUpdateTime);
    }

    //@Bindable
    public MutableLiveData<String> getNoteLabel() {
        return noteLabel;
    }

    public void setNoteLabel(MutableLiveData<String> noteLabel) {
        this.noteLabel = noteLabel;
    }

    //@Bindable
    public MutableLiveData<String> getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(MutableLiveData<String> noteContent) {
        this.noteContent = noteContent;
    }

    //@Bindable
    public MutableLiveData<String> getNoteLastUpdateTime() {
        return noteLastUpdateTime;
    }

    public void setNoteLastUpdateTime(MutableLiveData<String> noteLastUpdateTime) {
        this.noteLastUpdateTime = noteLastUpdateTime;
    }
}
