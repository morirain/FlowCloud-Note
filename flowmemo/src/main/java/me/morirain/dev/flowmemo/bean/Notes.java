package me.morirain.dev.flowmemo.bean;


import android.arch.lifecycle.MutableLiveData;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class Notes{

    public MutableLiveData<String> noteLabel = new MutableLiveData<>();
    public MutableLiveData<String> noteContent = new MutableLiveData<>();
    public MutableLiveData<String> noteLastUpdateTime = new MutableLiveData<>();

    public Notes(String noteLabel, String noteContent, String noteLastUpdateTime) {
        this.noteLabel.setValue(noteLabel);
        this.noteContent.setValue(noteContent);
        this.noteLastUpdateTime.setValue(noteLastUpdateTime);
    }
}
