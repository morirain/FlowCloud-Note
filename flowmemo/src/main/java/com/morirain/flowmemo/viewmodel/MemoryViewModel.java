package com.morirain.flowmemo.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.base.BaseViewModel;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/9
 */


public class MemoryViewModel extends BaseViewModel {

    public MutableLiveData<String> inputText = new MutableLiveData<>();

    public MemoryViewModel() {
    }

}
