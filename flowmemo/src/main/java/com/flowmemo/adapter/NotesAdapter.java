package com.flowmemo.adapter;


import android.support.annotation.NonNull;

import com.flowmemo.base.BaseAdapter;
import com.flowmemo.base.BasePresenter;
import com.flowmemo.databinding.ItemNotesBinding;
import com.flowmemo.model.Notes;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class NotesAdapter extends BaseAdapter<Notes, ItemNotesBinding> {

    public NotesAdapter(int variableId, int layoutId, BasePresenter<ItemNotesBinding> presenter) {
        super(variableId, layoutId, presenter);
    }

    @Override
    protected void setPosition(ViewHolder holder) {
        getBinding().buttonNotesMore.setTag(holder);
    }
}
