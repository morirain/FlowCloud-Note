package com.morirain.flowmemo.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.utils.EditTextMonitor;
import com.morirain.flowmemo.utils.SingleLiveEvent;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/9
 */


public class NotesContentViewModel extends BaseViewModel {

    public Notes note;

    private SingleLiveEvent<Boolean> isLabelChangeEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<String> setDefaultLabelEvent = new SingleLiveEvent<>();

    private SingleLiveEvent<Boolean> isContentChangeEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<String> setDefaultContentEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Void> onUndoClickEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Void> onRedoClickEvent = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> getIsLabelChangeEvent() {
        if (isLabelChangeEvent.getValue() == null) isLabelChangeEvent.setValue(false);
        return isLabelChangeEvent;
    }

    public SingleLiveEvent<Boolean> getIsContentChangeEvent() {
        if (isContentChangeEvent.getValue() == null) isContentChangeEvent.setValue(false);
        return isContentChangeEvent;
    }

    public NotesContentViewModel() {
    }

}
