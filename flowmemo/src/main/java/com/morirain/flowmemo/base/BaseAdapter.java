package com.morirain.flowmemo.base;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.morirain.flowmemo.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */

public class BaseAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    // Binding
    private B mBind;
    // Lifecycle
    private LifecycleOwner mLifecycleOwner;
    // List Data
    private MutableLiveData<List<T>> mDataList = new MutableLiveData<>();
    // onClickHandler
    private BaseCommandHandler mAdapterHandler;
    // BR Id
    private int mVariableId;
    // Layout Id
    private int mLayoutId;

    private List<T> getListValue() {
        if (mDataList != null) return mDataList.getValue();
        throw new NullPointerException();
    }

    public MutableLiveData<List<T>> getDataList() {
        return mDataList;
    }

    /*public BaseAdapter(int variableId, int layoutId) {
            this.mVariableId = variableId;
            this.mLayoutId = layoutId;
            mDataList.setValue(new ArrayList<>());
        }*/
    public BaseAdapter(LifecycleOwner lifecycleOwner, int variableId, int layoutId) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mVariableId = variableId;
        this.mLayoutId = layoutId;
        mDataList.setValue(new ArrayList<>());

    }

    public void setHandler(BaseCommandHandler handler) {
        mAdapterHandler = handler;
    }

    @NonNull
    @Override
    public ViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        if (mLifecycleOwner != null) mBind.setLifecycleOwner(mLifecycleOwner);
        return new ViewHolder<>(mBind);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.itemView.setTag(holder);
        holder.getBinding().setVariable(mVariableId, getListValue().get(position));
        if (mAdapterHandler != null) holder.getBinding().setVariable(BR.handler, mAdapterHandler);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : getListValue().size();
    }

    public class ViewHolder<D extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private D binding;

        ViewHolder(D binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setTag(this);
        }

        D getBinding() {
            return binding;
        }

        int getDataPosition() {
            return getLayoutPosition();
        }
    }
}