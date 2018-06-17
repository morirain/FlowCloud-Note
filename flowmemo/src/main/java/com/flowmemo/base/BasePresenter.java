package com.flowmemo.base;


import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.View;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public abstract class BasePresenter<B extends ViewDataBinding> implements BaseAdapter.AdapterPresenter<B> {
    protected int getPosition(View view) {
        BaseAdapter.ViewHolder viewHolder = (BaseAdapter.ViewHolder) view.getTag();
        return (int) viewHolder.itemView.getTag();
    }
}
