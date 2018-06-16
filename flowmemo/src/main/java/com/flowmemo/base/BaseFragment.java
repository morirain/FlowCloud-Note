package com.flowmemo.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/8
 */


public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment {

    private T mBind;

    private V mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getLayoutResId() <= 0) throw new AssertionError("Subclass must provide a valid layout resource id");

        mBind = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        mBind.setLifecycleOwner(this);

        initViewModel();
        setViewModel();
        init(savedInstanceState);
        if (getAdapter() != null) getAdapter().setLifecycleOwner(this);
        return mBind.getRoot();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(getViewModelClass());
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void setViewModel();

    protected abstract int getLayoutResId();

    protected BaseAdapter getAdapter() {
        return getViewModel().getAdapter();
    }

    protected View getRoot() {
        return mBind.getRoot();
    }

    protected final T getBinding() {
        return mBind;
    }

    protected abstract Class<V> getViewModelClass();

    protected final V getViewModel() {
        return mViewModel;
    }


}
