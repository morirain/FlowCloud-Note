package com.morirain.flowmemo.model;


import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.databinding.ItemNotesBinding;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */

@Entity
public class Notes{

    @Id
    long boxId;

    @Transient
    public MutableLiveData<String> noteLabel = new MutableLiveData<>();
    @Transient
    public MutableLiveData<String> noteContent = new MutableLiveData<>();
    @Transient
    public MutableLiveData<String> noteLastUpdateTime = new MutableLiveData<>();
    @Transient
    public MutableLiveData<String> notePreview = new MutableLiveData<>();


    public Notes(String noteLabel, String noteContent, String noteLastUpdateTime, String notePreview) {
        this.noteLabel.setValue(noteLabel);
        this.noteContent.setValue(noteContent);
        this.noteLastUpdateTime.setValue(noteLastUpdateTime);
        this.notePreview.setValue(notePreview);
    }
}
