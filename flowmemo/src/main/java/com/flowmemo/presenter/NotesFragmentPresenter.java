package com.flowmemo.presenter;


import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flowmemo.base.BaseAdapter;
import com.flowmemo.base.BasePresenter;
import com.flowmemo.databinding.ItemNotesBinding;
import com.flowmemo.model.Notes;
import com.flowmemo.utils.LogUtil;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class NotesFragmentPresenter extends BasePresenter<ItemNotesBinding> {

    @Override
    public void setPresenter(ItemNotesBinding bind) {
        bind.setPresenter(this);
    }

    @Override
    public void onClick(View view) {
    }

    public void onItemClick(View view) {
        LogUtil.d(String.valueOf(view.getTag()));
    }

    public void newNote(View view) {
        LogUtil.d("dasdas");
    }

    public void onClickMore(View view) {
        LogUtil.d(String.valueOf(getPosition(view)));
    }

}
