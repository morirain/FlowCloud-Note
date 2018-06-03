package me.morirain.dev.flowmemo.bean;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public class Notes extends BaseObservable {

    private ObservableField<String> noteLabel;
    private ObservableField<String> noteContent;
    private ObservableField<String> noteLastUpdateTime;

    public Notes(String noteLabel, String noteContent, String noteLastUpdateTime) {
        this.noteLabel = new ObservableField<String>(noteLabel);
        this.noteContent = new ObservableField<String>(noteContent);
        this.noteLastUpdateTime = new ObservableField<String>(noteLastUpdateTime);
    }

    public ObservableField<String> getNoteLabel() {
        return noteLabel;
    }

    public void setNoteLabel(ObservableField<String> noteLabel) {
        this.noteLabel = noteLabel;
    }

    public ObservableField<String> getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(ObservableField<String> noteContent) {
        this.noteContent = noteContent;
    }

    public ObservableField<String> getNoteLastUpdateTime() {
        return noteLastUpdateTime;
    }

    public void setNoteLastUpdateTime(ObservableField<String> noteLastUpdateTime) {
        this.noteLastUpdateTime = noteLastUpdateTime;
    }
}
