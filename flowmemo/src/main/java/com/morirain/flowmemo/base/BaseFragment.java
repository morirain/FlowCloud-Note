package com.morirain.flowmemo.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morirain.flowmemo.BR;

import java.io.Serializable;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/8
 */


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements Serializable {

    private T mBind;

    private V mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getLayoutResId() <= 0)
            throw new AssertionError("Subclass must provide a valid layout resource id");

        mBind = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        mBind.setLifecycleOwner(this);

        if (getViewModelClass() != null) initViewModel();
        if (getHandler() != null) setHandler();
        setCustomViewModelConnect();
        init(savedInstanceState);
        return mBind.getRoot();
    }

    private void initViewModel() {
        mViewModel = getNewViewModel(BR.viewModel, getViewModelClass());
    }

    protected <G extends BaseViewModel> G getNewViewModel(int br, @NonNull Class<G> modelClass) {
        G viewModel = ViewModelProviders.of(this).get(modelClass);
        mBind.setVariable(br, viewModel);
        return viewModel;
    }

    private void setHandler() {
        mBind.setVariable(BR.handler, getHandler());
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void setCustomViewModelConnect();

    protected abstract int getLayoutResId();

    protected abstract Class<V> getViewModelClass();

    protected abstract BaseCommandHandler getHandler();

    protected View getRoot() {
        return mBind.getRoot();
    }

    public final T getBinding() {
        return mBind;
    }

    protected final V getViewModel() {
        return mViewModel;
    }

}
