package com.morirain.flowmemo.viewmodel;


import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.utils.SingleLiveEvent;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/9
 */


public class NotesContentViewModel extends BaseViewModel {

    public MutableLiveData<String> notesTitle = new MutableLiveData<>();

    public MutableLiveData<String> notesContent = new MutableLiveData<>();

    public SingleLiveEvent<Void> requestFocusEvent = new SingleLiveEvent<>();

    public NotesContentViewModel() {
        notesTitle.postValue("标题");
    }

    public void onRequestFocusClick() {
        requestFocusEvent.call();
    }
}
