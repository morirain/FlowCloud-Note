package com.morirain.flowmemo.adapter;


import android.arch.lifecycle.LifecycleOwner;

import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.databinding.ItemNotesBinding;
import com.morirain.flowmemo.model.Notes;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/16
 */


public class NotesAdapter extends BaseAdapter<Notes, ItemNotesBinding> {

    public NotesAdapter(LifecycleOwner lifecycleOwner, int variableId, int layoutId) {
        super(lifecycleOwner, variableId, layoutId);
    }

    /*@Override
    protected void setPosition(int position) {
        //getBinding().buttonNotesMore.setTag(holder);
        //getBinding().getRoot().setTag(position);
    }*/
}
