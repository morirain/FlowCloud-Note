package me.morirain.dev.flowmemo.base;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private MutableLiveData<List<T>> mList;

    private int mVariableId;
    private int mLayoutId;

    public MutableLiveData<List<T>> getList() {
        return mList;
    }

    public BaseAdapter(LifecycleOwner lifecycleOwner, int variableId, int layoutId) {
        this.mList = new MutableLiveData<>();
        this.mVariableId = variableId;
        this.mLayoutId = layoutId;
        mList.setValue(new ArrayList<T>());
        this.mList.observe(lifecycleOwner, (List<T> ts) -> notifyDataSetChanged());
    }

    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setVariable(mVariableId, mList.getValue().get(position));
    }

    /**
     * 刷新数据
     *
     * @param data 数据源
     */
    public void refresh(List<T> data) {
        if (mList.getValue() != null) mList.getValue().clear();
        mList.getValue().addAll(data);
        //notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.getValue().size();
    }

    public class ViewHolder<T> extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}