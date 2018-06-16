package com.flowmemo.base;


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

public abstract class BaseAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    private B mBind;

    private LifecycleOwner mLifecycleOwner;

    public MutableLiveData<List<T>> DataList = new MutableLiveData<>();

    private AdapterPresenter<B> mAdapterPresenter;

    private int mVariableId;
    private int mLayoutId;

    private List<T> getListValue() {
        if (DataList != null) return DataList.getValue();
        throw new NullPointerException();
    }

    public BaseAdapter(int variableId, int layoutId) {
        this.mVariableId = variableId;
        this.mLayoutId = layoutId;
        DataList.setValue(new ArrayList<>());

    }
    public BaseAdapter(int variableId, int layoutId, BasePresenter<B> presenter) {
        this.mVariableId = variableId;
        this.mLayoutId = layoutId;
        DataList.setValue(new ArrayList<>());
        if (presenter != null) mAdapterPresenter = presenter;

    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
    }

    public interface AdapterPresenter<A extends ViewDataBinding> {
        void setPresenter(A bind);
    }

    public void setAdapterPresenter(AdapterPresenter<B> adapterPresenter) {
        mAdapterPresenter = adapterPresenter;
    }

    protected B getBinding() {
        if (mBind != null) return mBind;
        return null;
    }

    @NonNull
    @Override
    public ViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        if (mAdapterPresenter != null) {
            mAdapterPresenter.setPresenter(mBind);
        }
        if (mLifecycleOwner != null) {
            mBind.setLifecycleOwner(mLifecycleOwner);
        }
        return new ViewHolder<>(mBind.getRoot(), mBind);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        setPosition(holder);
        holder.getBinding().setVariable(mVariableId, getListValue().get(position));
    }

    protected abstract void setPosition(ViewHolder holder);

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
        return DataList == null ? 0 : getListValue().size();
    }

    public class ViewHolder<C> extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        ViewHolder(View itemView, ViewDataBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        ViewDataBinding getBinding() {
            return binding;
        }
    }
}