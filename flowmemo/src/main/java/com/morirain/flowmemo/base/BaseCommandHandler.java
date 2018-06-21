package com.morirain.flowmemo.base;


import android.databinding.ViewDataBinding;
import android.view.View;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public abstract class BaseCommandHandler {
    protected int getPosition(View view) {
        //BaseAdapter.ViewHolder viewHolder = (BaseAdapter.ViewHolder) view.getTag();
        //return (int) viewHolder.itemView.getTag();
        BaseAdapter.ViewHolder viewHolder = (BaseAdapter.ViewHolder) view.getRootView().getTag();
        return viewHolder.getDataPosition();
    }
}
