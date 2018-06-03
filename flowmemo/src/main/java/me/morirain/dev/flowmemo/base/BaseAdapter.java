package me.morirain.dev.flowmemo.base;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private List<T> mList;
    private int mVariableId;
    private int mLayoutId;

    protected List<T> getList() {
        return mList;
    }

    public BaseAdapter(int variableId, int layoutId) {
        this.mList = new ArrayList<>();
        this.mVariableId = variableId;
        this.mLayoutId = layoutId;
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
        holder.binding.setVariable(mVariableId, mList.get(position));
    }

    /**
     * 刷新数据
     *
     * @param data 数据源
     */
    public void refresh(List<T> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    public class ViewHolder<T> extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }
}