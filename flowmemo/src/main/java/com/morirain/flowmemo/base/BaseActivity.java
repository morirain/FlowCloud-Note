package com.morirain.flowmemo.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.morirain.flowmemo.BR;

/**
 * Created by morirain on 2018/6/2.
 * E-Mail Address：morirain.dev@outlook.com
 */


public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity {

    private T mBind;

    private V mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() <= 0)
            throw new AssertionError("Subclass must provide a valid layout resource id");

        mBind = DataBindingUtil.setContentView(this, getLayoutResId());
        mBind.setLifecycleOwner(this);

        if (getViewModelClass() != null) initViewModel();
        if (getHandler() != null) setHandler();
        setCustomViewModelConnect();
        init(savedInstanceState);
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(getViewModelClass());
        setViewModel(BR.viewModel, mViewModel);
    }

    protected void setViewModel(int br, @NonNull BaseViewModel viewModel) {
        mBind.setVariable(br, viewModel);
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

    protected final T getBinding() {
        return mBind;
    }
    protected final V getViewModel() {
        return mViewModel;
    }
}