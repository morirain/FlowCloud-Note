package com.morirain.flowmemo.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.morirain.flowmemo.BR;


/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
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

    public void switchFragment(int containerViewId, Fragment from, Fragment toHide) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (!from.isAdded()) {
            fragmentTransaction//.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .add(containerViewId, from)
                    .show(from);
        } else {
            fragmentTransaction.show(from);
        }
        if (toHide != null) fragmentTransaction.hide(toHide);
        fragmentTransaction.commit();
    }
}