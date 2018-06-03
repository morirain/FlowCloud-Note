package me.morirain.dev.flowmemo.bean;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.Bindable;

import me.morirain.dev.flowmemo.BR;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class Notes{

    private MutableLiveData<String> noteLabel;
    private MutableLiveData<String> noteContent;
    private MutableLiveData<String> noteLastUpdateTime;

    public Notes(MutableLiveData<String> noteLabel, MutableLiveData<String> noteContent, MutableLiveData<String> noteLastUpdateTime) {
        this.noteLabel = noteLabel;
        this.noteContent = noteContent;
        this.noteLastUpdateTime = noteLastUpdateTime;
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
