package com.morirain.flowmemo.base;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.morirain.flowmemo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public abstract class BaseCommandHandler {

    protected int getPosition(View view) {
        if (view.getTag() instanceof RecyclerView.ViewHolder) {
            BaseAdapter.ViewHolder viewHolder = (BaseAdapter.ViewHolder) view.getTag();
            return (int) viewHolder.itemView.getTag();
        }
        return (int) view.getTag();
    }
}
