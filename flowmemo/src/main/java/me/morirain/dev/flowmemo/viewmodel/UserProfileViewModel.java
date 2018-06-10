package me.morirain.dev.flowmemo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import me.morirain.dev.flowmemo.base.BaseViewModel;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/10
 */


public class UserProfileViewModel extends BaseViewModel {

    private MutableLiveData<String> mUserName;
    private MutableLiveData<String> mUserEmail;

    public LiveData<String> getUserName() {
        if (mUserName == null) {
            mUserName = new MutableLiveData<>();
            mUserName.setValue("empty name");
        }
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName.setValue(userName);
    }

    public LiveData<String> getUserEmail() {
        if (mUserEmail == null) {
            mUserEmail = new MutableLiveData<>();
            mUserEmail.setValue("empty@email.com");
        }
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        mUserEmail.setValue(userEmail);
    }
    @Override
    protected void init() {

    }
}
