package me.morirain.dev.flowmemo.base;

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


public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {

    private T mBind;

    protected abstract int getLayoutResId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getLayoutResId() <= 0) throw new AssertionError("Subclass must provide a valid layout resource id");

        mBind = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);
        mBind.setLifecycleOwner(this);

        init(savedInstanceState);

        return mBind.getRoot();
    }

    protected abstract void init(Bundle savedInstanceState);

    protected View getRoot() {
        return mBind.getRoot();
    }

    protected final T getBinding() {
        return mBind;
    }


}
