package me.morirain.dev.flowmemo.base;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
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

    private MutableLiveData<List<T>> mList;
    private List<T> getListValue(){
        if (mList != null) return mList.getValue();
        throw new NullPointerException();
    }

    private int mVariableId;
    private int mLayoutId;

    public MutableLiveData<List<T>> getList() {
        return mList;
    }

    public BaseAdapter(LifecycleOwner lifecycleOwner, int variableId, int layoutId) {
        this.mVariableId = variableId;
        this.mLayoutId = layoutId;
        this.mList = new MutableLiveData<>();
        this.mList.observe(lifecycleOwner, ts -> notifyDataSetChanged());
        mList.setValue(new ArrayList<>());
    }

    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        ViewHolder<T> viewHolder = new ViewHolder<>(binding.getRoot());
        viewHolder.setBinding(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setVariable(mVariableId, getListValue().get(position));
    }

    /**
     * 刷新数据
     *
     * @param data 数据源
     */
    public void refresh(List<T> data) {
        getListValue().clear();
        getListValue().addAll(data);
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : getListValue().size();
    }

    public class ViewHolder<B> extends RecyclerView.ViewHolder {

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