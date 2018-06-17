package com.morirain.flowmemo.base;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class BaseBindingAdapter {
    /*@BindingAdapter("setPresenter")
    public static void setPresenter(View view, View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
    }
    @BindingAdapter("setPresenter")
    public static void setPresenter(View view, View.OnLongClickListener clickListener) {
        view.setOnLongClickListener(clickListener);
    }*/
    @BindingAdapter({"setAdapter"})
    public static void setAdapter(RecyclerView view, BaseAdapter baseAdapter) {
        view.setAdapter(baseAdapter);
    }
}
