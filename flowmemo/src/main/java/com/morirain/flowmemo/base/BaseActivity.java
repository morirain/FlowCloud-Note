package com.morirain.flowmemo.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    protected abstract void init(Bundle savedInstanceState);

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

    public void switchFragment(int containerViewId, BaseFragment from, Fragment toHide) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().executePendingTransactions();
        if (!from.getAdded()) {
            fragmentTransaction
                    .add(containerViewId, from)
                    .show(from);
            from.setAdded(true);
        } else {
            fragmentTransaction.show(from);
        }

        if (toHide != null) fragmentTransaction.hide(toHide);
        fragmentTransaction.commit();
    }
}