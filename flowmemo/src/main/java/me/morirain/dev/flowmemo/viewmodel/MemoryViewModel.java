package me.morirain.dev.flowmemo.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import me.morirain.dev.flowmemo.base.BaseViewModel;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/9
 */


public class MemoryViewModel extends BaseViewModel {

    public MutableLiveData<String> inputText = new MutableLiveData<>();

    @Override
    protected void init() {

    }
}
