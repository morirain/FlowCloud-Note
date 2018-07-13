package com.morirain.flowmemo.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.morirain.flowmemo.BR;
import com.morirain.flowmemo.BuildConfig;
import com.morirain.flowmemo.ui.activity.ContainerActivity;
import com.morirain.flowmemo.utils.SingleLiveEvent;

import java.io.Serializable;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/8
 */


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements Serializable {

    private T mBind;

    private V mViewModel;

    private View mView;

    private boolean mAdded = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getLayoutResId() <= 0)
            throw new AssertionError("Subclass must provide a valid layout resource id");


        if (mView == null) mView = inflater.inflate(getLayoutResId(), container, false);
        mBind = DataBindingUtil.bind(mView);
        if (mBind != null) mBind.setLifecycleOwner(this);

        if (getViewModelClass() != null) initViewModel();
        setArguments();

        init(savedInstanceState);
        return mView;
    }

    public void setAdded(boolean added) {
        mAdded = added;
    }

    public boolean getAdded() {
        return mAdded;
    }

    private void initViewModel() {
        mViewModel = getNewViewModel(BR.viewModel, getViewModelClass());
    }

    protected <G extends BaseViewModel> G getNewViewModel(int br, @NonNull Class<G> modelClass) {
        // Share ViewModel
        if (getActivity() != null) {
            G viewModel;
            viewModel = ViewModelProviders.of(getActivity()).get(modelClass);
            mBind.setVariable(br, viewModel);
            return viewModel;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void setArguments();

    protected abstract int getLayoutResId();

    protected abstract Class<V> getViewModelClass();

    protected View getRoot() {
        return mBind.getRoot();
    }

    protected final T getBinding() {
        return mBind;
    }

    protected final V getViewModel() {
        return mViewModel;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            BaseApplication.sRefWatcher.watch(this);
        }
    }

    public void switchActivity(BaseFragment fragment) {
        Intent intent = new Intent(getActivity(), ContainerActivity.class);
        intent.putExtra("fragment", fragment.hashCode());
        ContainerActivity.setFragment(fragment.hashCode(), fragment);
        startActivity(intent);
    }

}
