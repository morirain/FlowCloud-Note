package com.morirain.flowmemo.base;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morirain.flowmemo.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */

public class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    // Binding
    private ViewDataBinding mBind;
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

    public BaseAdapter(LifecycleOwner lifecycleOwner, int layoutId) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mVariableId = BR.item;
        this.mLayoutId = layoutId;
        this.mDataList.setValue(new ArrayList<>());
    }

    public void setHandler(BaseCommandHandler handler) {
        mAdapterHandler = handler;
    }

    public void setVariableId(int variableId) {
        this.mVariableId = variableId;
    }

    @NonNull
    @Override
    public ViewHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        if (mLifecycleOwner != null) mBind.setLifecycleOwner(mLifecycleOwner);
        return new ViewHolder<>(mBind);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
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

        private void setViewChildTag(View view) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View child = viewGroup.getChildAt(i);
                    if (child.isClickable() || child.isLongClickable()) child.setTag(this);
                    setViewChildTag(child);
                }
            }
        }

        ViewHolder(D binding) {
            super(binding.getRoot());
            this.binding = binding;
            // setTag(this)
            setViewChildTag(itemView);
        }

        D getBinding() {
            return binding;
        }
    }
}