package me.morirain.dev.flowmemo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import me.morirain.dev.flowmemo.base.BaseViewModel;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/9
 */


public class MainViewModel extends BaseViewModel {

    private MutableLiveData<String> mProfileName;
    private MutableLiveData<String> mProfileEmail;

    @Override
    protected void init() {

    }
    public LiveData<String> getProfileName() {
        if (mProfileName == null) {
            mProfileName = new MutableLiveData<>();
            mProfileName.setValue("empty name");
        }
        return mProfileName;
    }

    public void setProfileName(String profileName) {
        mProfileName.setValue(profileName);
    }
    public LiveData<String> getProfileEmail() {
        if (mProfileEmail == null) {
            mProfileEmail = new MutableLiveData<>();
            mProfileEmail.setValue("empty@email.com");
        }
        return mProfileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        mProfileEmail.setValue(profileEmail);
    }
}
