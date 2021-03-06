package com.morirain.flowmemo.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.morirain.flowmemo.base.BaseViewModel;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/10
 */


public class UserProfileViewModel extends BaseViewModel {

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userEmail = new MutableLiveData<>();

    public UserProfileViewModel(){
        userName.postValue("empty name");
        userEmail.postValue("empty@email.com");
    }
}
